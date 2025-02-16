package science.com.master.sonar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ThreadedETLExecutor {

    public final String SONAR_COMPONENT = "igorregis_sonarvsllm";

    /**
     * Object Mapper usado para fazer parsing das respostas JSON
     */
    protected ObjectMapper OBJECT_MAPPER;

    protected String PAPER = "controlled";

    public static final String SCENARIO_ORIGINAL = "Original";
    public static final String SCENARIO_NO_COMMENTS = "NoComments";
    public static final String SCENARIO_BAD_NAMES = "BadNames";
    public static final String SCENARIO_BAD_NAMES_NO_COMMENTS = "BadNamesNoComments";
    public static final String SCENARIO_CLEAN_CODE = "CleanCode";

    public static final String SCENARIO_BUSE_AND_WEIMER = "BuseAndWeimer";
    public static final String SCENARIO_DORN = "Dorn";
    public static final String SCENARIO_SCALABRINO = "Scalabrino";
    public static final String SCENARIO_QUARKUS = "Quarkus";
    public static final String SCENARIO_SPD = "SPD";

    public static final Map<@NotNull String, @NotNull String> BRANCHES = Map.of(SCENARIO_ORIGINAL, "main", SCENARIO_NO_COMMENTS, "no_comments",
            SCENARIO_BAD_NAMES, "bad_names", SCENARIO_BAD_NAMES_NO_COMMENTS, "bad_names_no_comments", SCENARIO_CLEAN_CODE, "clean_code",
            SCENARIO_BUSE_AND_WEIMER, "buse_and_weimer", SCENARIO_DORN, "born", SCENARIO_SCALABRINO, "scalabrino", SCENARIO_QUARKUS, "quarkus", SCENARIO_SPD, "spd");

    public static final Map<@NotNull String, @NotNull String> NO_BRANCHES = Map.of(SCENARIO_BUSE_AND_WEIMER, "buse_and_weimer", SCENARIO_DORN, "born",
            SCENARIO_SCALABRINO, "scalabrino", SCENARIO_QUARKUS, "quarkus", SCENARIO_SPD, "spd");

    /**
     * Número máximo de Threads a serem disparadas para realizar chamadas simultâneas ao Chat GPT
     */
    protected static final int THREADS_GPT = 10;

    protected String PAPER_PREFIX = PAPER;

    protected String CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + PAPER + File.separator;

    protected String BASE_FILE_SYSTEM = "/home/igor/IdeaProjects/sonarvsllm/sonarvsllm-testcases/src/main/resources/" + PAPER + "/";

    protected String INPUT_BATCH_JSON_SUFIX = getLLMModel() + "-input.json";

    protected String LLM_INPUT_BATCH_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + INPUT_BATCH_JSON_SUFIX;

    protected String OUTPUT_JSON_SUFIX = getLLMModel() + ".json";

    protected String LLM_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + OUTPUT_JSON_SUFIX;

    /**
     * System prompt enviado ao LLM
     */
    protected final StringBuilder SYSTEM_PROMPT = new StringBuilder();

    protected Logger logger;

    protected Vector<Thread> threads = new Vector<>();

    protected Vector<String> threadsCounter = new Vector<>();

    @ConfigProperty(name = "quarkus.datasource.jdbc.min-size")
    protected int threadNumber;

    @Inject
    protected SonarClient sonarClient;

    protected HttpClient httpClient = null;

    /**
     * Contador de requests, usamos ele para registrar no log a quantidade de análises realizadas
     */
    protected int requestCount;

    /**
     * Contador de tokens usados, usamos ele para saber quanto estamos gastando com as análises, em tempo de execução
     */
    protected Integer totalTokensUsed = 0;

    /**
     * Este semáforo é usado para limitar a quantidade de Threadss simultâneas ao limite suportado pelo pool de conexões do banco de dados,
     * geralmente calibrado conforme o rate limit da API do LLM (algo em torno de 15)
     */
    protected Semaphore controleThreads;

    protected int batchEntryId = 1;

    /**
     * Inicio do prompt do usuário
     */
    protected String USER_PROMPT_START;

    /**
     * Marcador do final do prompt do usuário
     */
    protected String USER_PROMPT_END;

    public ThreadedETLExecutor() {OBJECT_MAPPER = new ObjectMapper();
        USER_PROMPT_START = "Evaluate the following Java source code: ";
        USER_PROMPT_END = "This is the end of the class file, the assistant should present your json answer:";
    }

    protected static void assertCreated(String file) {
        File outputFile = new File(file);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract String getLLMModel();

    @PostConstruct
    public void init() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    protected void waitThreads() {
        if (threadsCounter != null && !threadsCounter.isEmpty()) {
            logger.warning("Join das Threads " + threadsCounter.size());
            // Aguarde a conclusão de todas as VirtualThreads
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Erro ao aguardar a conclusão da VirtualThread", e);
                }
            }
            logger.warning("Fim do processo " + this.getClass().getName());
        }
    }

    /**
     * Configura o cliente HTTP se ainda não estiver configurado.
     * Este método cria um novo HttpClient com um pool de threads fixo e um semáforo para controlar o número de threads.
     * O número de threads é definido pela constante THREADS_GPT
     * O HttpClient e o semáforo são armazenados como variáveis de instância para uso posterior.
     */
    protected void setupHttpClient() {
        if (httpClient == null) {
            ExecutorService executor = Executors.newFixedThreadPool(THREADS_GPT);
            httpClient = HttpClient.newBuilder().executor(executor).build();
            controleThreads = new Semaphore(THREADS_GPT);
        }
    }

    protected void processLLMResponse(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, String scenario, boolean createInputBatch, HttpRequest request) {
        try {
            controleThreads.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<Void> responseEval = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> avaliaOcorrenciaDeErro(classAndPackage, systemPrompt, userPromptStart, javaClass, commandEnd, resp, scenario, createInputBatch))
                .thenApply(stringHttpResponse -> parseGPTResponse(stringHttpResponse == null ? null : stringHttpResponse.body())).thenApply(response -> buildGTPResponse(classAndPackage, response, scenario))
                .thenAccept(diffGPTResponse -> registraAvaliacao(classAndPackage, diffGPTResponse));

        responseEval.join();
        controleThreads.release();
    }

    /**
     * %
     * Este método registra a avaliação do GPT no banco de dados
     * Ele inicia uma transação, encontra o CommitDiff correspondente no banco de dados e atualiza seus campos com os valores da avaliação.
     * Em seguida, persiste as alterações e faz o commit da transação.
     * Se ocorrer um erro durante esse processo, ele é registrado e a thread é liberada.
     *
     * @param fileName   Java Class File Name
     * @param evaluation A avaliação do GPT para o CommitDiff.
     */
    protected synchronized void registraAvaliacao(String fileName, GPTResponse evaluation) {
        try {
            if (evaluation != null) {
                logger.warning("Evaluation result for " + fileName + ": \n" + evaluation);
                logger.warning("Output file " + LLM_JSON);
                Files.write(Paths.get(LLM_JSON), (evaluation + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, fileName + ": " + evaluation, e);
        }
    }

    /**
     * Este método monta a response do GPT.
     * Ele usa o ObjectMapper para fazer o parse da response do GPT e cria um objeto DiffGPTResponse.
     * Se a pontuação termina com "%", ele remove o símbolo de percentagem, para usarmos o valor como um inteiro.
     * Se a análise tem mais de 500 caracteres, ele a trunca para 500 caracteres, pois este é o tamamnho do campo no DB,
     * esse tratamento é necessário pois o LLM costuma não respeitar estes limites no prompt.
     *
     * @param classAndPackage
     * @param response        A response do GPT em formato de string (esperamos um json).
     * @param scenario
     * @return O objeto DiffGPTResponse montado a partir da response. Retorna null se ocorrer um erro ao fazer o parse da response do GPT.
     */
    protected GPTResponse buildGTPResponse(String classAndPackage, String response, String scenario) {
        if (response == null) {
            return null;
        }
        try {
            GPTResponse evaluation = OBJECT_MAPPER.readValue(response, new TypeReference<>() {});
            if (evaluation.score.endsWith("%")) {//Remove the % sign
                evaluation.score = evaluation.score.substring(0, evaluation.score.length() - 1);
            }
            decorateData(classAndPackage, scenario, evaluation);
            return evaluation;
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Error building DiffGPTResponse " + response, e);
            return null;
        }

    }

    protected void decorateData(String classAndPackage, String scenario, GPTResponse evaluation) {
        if (NO_BRANCHES.containsValue(scenario)) {
            evaluation.name = classAndPackage;
        }else {
            //If it's a scenario that we have analysis by SonarQube, we get the data from SonarQube
            evaluation.sonarData = sonarClient.getComponentMeasures(SONAR_COMPONENT + "%3A" + classAndPackage, scenario);
        }
    }

    protected abstract String parseGPTResponse(String o);

    protected abstract HttpResponse<String> avaliaOcorrenciaDeErro(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, HttpResponse<String> resp, String scenario, boolean createInputBatch);

    protected abstract String @NotNull [] getScenarios();

    protected abstract void evaluateFiles(boolean createInputBatch, String scenario);

    protected abstract String getPaper();

    @ActivateRequestContext
    public void run(boolean createInputBatch) {
        PAPER = getPaper();
        BASE_FILE_SYSTEM = "/home/igor/IdeaProjects/sonarvsllm/sonarvsllm-testcases/src/main/resources/" + PAPER + "/";
        INPUT_BATCH_JSON_SUFIX = getLLMModel() + "-input.json";
        LLM_INPUT_BATCH_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + INPUT_BATCH_JSON_SUFIX;
        OUTPUT_JSON_SUFIX = getLLMModel() + ".json";
        LLM_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + OUTPUT_JSON_SUFIX;

        setupHttpClient();

        String[] scenarios = getScenarios();
        String likert = "";
        for (String scenario : scenarios) {
            batchEntryId = 1;
            int iteractions;
            if (NO_BRANCHES.containsKey(scenario)) {
//                likert = "-likert-";
                iteractions = 1; // We execute 10 times for previous works datasets
                redefinePrompt(); // We redefine the prompt to expect code snippets instead of full classes
            } else {
                iteractions = 10; //We execute 100 times for our controlled inerventions
            }
            for (int i=1; i<=iteractions; i++) {
                OUTPUT_JSON_SUFIX = getLLMModel() + "-" + i + ".json";

                PAPER_PREFIX = PAPER + scenario;
                LLM_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + likert + OUTPUT_JSON_SUFIX;
                LLM_INPUT_BATCH_JSON = BASE_FILE_SYSTEM + getLLMModel() + "/" + PAPER_PREFIX + INPUT_BATCH_JSON_SUFIX;

                if (createInputBatch) {
                    assertCreated(LLM_INPUT_BATCH_JSON);
                } else {
                    assertCreated(LLM_JSON);
                }
                CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + PAPER + File.separator + scenario;
                evaluateFiles(createInputBatch, BRANCHES.get(scenario));
            }

        }
    }

    private void redefinePrompt() {
        SYSTEM_PROMPT.setLength(0);
        SYSTEM_PROMPT.append("The assistant is a seasoned senior software engineer, with deep programming expertise on Java/Python/C, ");
        SYSTEM_PROMPT.append("doing source code evaluation as part of a due diligence process, these source code are presented in the form of code snippets. ");
//        SYSTEM_PROMPT.append("Your task is to assign a score on a 1-5 likert scale, from 1 (very unreadable) to 5 (very readable) based on the readability level.\n");
        SYSTEM_PROMPT.append("Your task is to assign a score from 0 to 100 based on the readability level and overall ease of comprehension.\n");
        SYSTEM_PROMPT.append("Your answers MUST be presented ONLY in the following json format: {\"score\":\"N\", \"reasoning\":\"your explanation about the score\" } ");
        SYSTEM_PROMPT.append("- The \"explanation\" attribute must not surpass 450 characters and MUST NOT contain especial characters or new lines\n");

        USER_PROMPT_START = "Evaluate the following code snippet: ";
        USER_PROMPT_END = "This is the end of the code snippet, the assistant should present your json answer:";
    }
}
