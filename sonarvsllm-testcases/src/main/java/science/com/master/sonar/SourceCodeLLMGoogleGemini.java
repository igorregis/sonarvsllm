package science.com.master.sonar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 * Classe responsável por realizar análise de diffs usando um LLM. Ela carrega todos os diffs da base, que devem ser avaliados e atribui uma avaliação a eles.
 * As avaliações são solicitadas ao LLM em um formato JSON com 2 atributos:<br></>
 * Score - Que indica uma nota de 1 a 100 para o código
 * Analise - Que deve conter um texto de até 500 caracteres explicando o racional da análisez, no prompt pedimos para o LLM ater-se a 450 caracteres, mas ele sempre estoura.
 * <p>
 * <p>
 * Selected Files from Quarkus Project with less tham 800 lines of code:
 * Como mostrar este comando em um documento latex?
 * Executar de dentro da pasta core do projeto:
 */
@ApplicationScoped
public class SourceCodeLLMGoogleGemini extends ThreadedETLExecutor {

    public static final String LLM_MODEL = "Gemini15flash";
//    public static final String LLM_MODEL = "Gemini20flash";
//    public static final String LLM_MODEL = "Gemini15pro";

    private final Map<String, String> MODEL_PARAM = Map.of("Gemini15flash", "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent",
                                                            "Gemini20flash", "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent",
                                                            "Gemini15pro", "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-001:generateContent");

    public final String URL_LLM = MODEL_PARAM.get(LLM_MODEL);

    public static String OUTPUT_JSON_SUFIX = LLM_MODEL + ".json";

    /**
     * Número máximo de Threads a serem disparadas para realizar chamadas simultâneas ao Chat GPT
     */
    public static final int THREADS_GPT = 10;
    public static final String SCENARIO_ORIGINAL = "Original";
    public static final String SCENARIO_NO_COMMENTS = "NoComments";
    public static final String SCENARIO_BAD_NAMES = "BadNames";
    public static final String SCENARIO_BAD_NAMES_NO_COMMENTS = "BadNamesNoComments";
    public static final String SCENARIO_CLEAN_CODE = "CleanCode";
    public static final Map<@NotNull String, @NotNull String> BRANCHES = Map.of(SCENARIO_ORIGINAL, "main", SCENARIO_NO_COMMENTS, "no_comments",
            SCENARIO_BAD_NAMES, "bad_names", SCENARIO_BAD_NAMES_NO_COMMENTS, "bad_names_no_comments", SCENARIO_CLEAN_CODE, "clean_code");

    public static String CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + "controlled" + File.separator;

    public static String CONTROLLED_SCENARIO = "controlled";

    public static String LLM_JSON = "/home/igor/IdeaProjects/sonarvsllm/sonarvsllm-testcases/src/main/resources/controlled/" + LLM_MODEL + "/" + CONTROLLED_SCENARIO + OUTPUT_JSON_SUFIX;

    public static final String component = "igorregis_sonarvsllm";

    /**
     * System prompt enviado ao LLM
     */
    private final StringBuilder SYSTEM_PROMPT = new StringBuilder();

    /**
     * Inicio do prompt do usuário
     */
    private final String USER_PROMPT_START;

    /**
     * Marcador do final do prompt do usuário
     */
    private final String USER_PROMPT_END;

    private HttpClient httpClient = null;

    @ConfigProperty(name = "api.key.llmGemini")
    private String apiKeyLLM;

    @ConfigProperty(name = "google.project")
    private String googleProject;

    /**
     * Contador de requests, usamos ele para registrar no log a quantidade de análises realizadas
     */
    private int requestCount;

    /**
     * Contador de tokens usados, usamos ele para saber quanto estamos gastando com as análises, em tempo de execução
     */
    private Integer totalTokensUsed = 0;

    /**
     * Este semáforo é usado para limitar a quantidade de Threadss simultâneas ao limite suportado pelo pool de conexões do banco de dados,
     * geralmente calibrado conforme o rate limit da API do LLM (algo em torno de 15)
     */
    private Semaphore controleThreads;

    /**
     * Object Mapper usado para fazer parsing das respostas JSON
     */
    private final ObjectMapper OBJECT_MAPPER;

    /**
     * Arquivo com os dados da analise feita com o GPT 4o
     */
    private HashMap<String, JsonNode> sonarDataGPT4o;

    public SourceCodeLLMGoogleGemini() {
        SYSTEM_PROMPT.append("The assistant is a seasoned senior software engineer, with deep Java Language expertise, ");
        SYSTEM_PROMPT.append("doing source code evaluation as part of a due diligence process, these source code are presented in the form of a Java Class File. ");
        SYSTEM_PROMPT.append("Your task is to emit a score from 0 to 100 based on the readability level of the source code presented.\n");
//        SYSTEM_PROMPT.append("Your task is to emit a score from 0 to 100 based on the readability level and overall quality of the source code presented.\n");
        SYSTEM_PROMPT.append("Your answers MUST be presented ONLY in the following json format: {\"score\":\"NN%\", \"reasoning\":\"your explanation about the score\" } ");
        SYSTEM_PROMPT.append("- The \"explanation\" attribute must not surpass 450 characters and MUST NOT contain especial characters or new lines\n");

        USER_PROMPT_START = "Evaluate the following Java source code: ";
        USER_PROMPT_END = "This is the end of the class file, the assistant should present your json answer:";

        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    }

    /**
     * Configura o cliente HTTP se ainda não estiver configurado.
     * Este método cria um novo HttpClient com um pool de threads fixo e um semáforo para controlar o número de threads.
     * O número de threads é definido pela constante THREADS_GPT
     * O HttpClient e o semáforo são armazenados como variáveis de instância para uso posterior.
     */
    private void setupHttpClient() {
        if (httpClient == null) {
            ExecutorService executor = Executors.newFixedThreadPool(THREADS_GPT);
            httpClient = HttpClient.newBuilder().executor(executor).build();
            controleThreads = new Semaphore(THREADS_GPT);
        }
    }


    @ActivateRequestContext
    public void run() {
        setupHttpClient();

        String[] scenarios = {SCENARIO_ORIGINAL, SCENARIO_NO_COMMENTS, SCENARIO_BAD_NAMES, SCENARIO_BAD_NAMES_NO_COMMENTS, SCENARIO_CLEAN_CODE};

        for (String scenario : scenarios) {
            for (int i=1; i<=100; i++) {
                OUTPUT_JSON_SUFIX = LLM_MODEL + "-" + i + ".json";

                CONTROLLED_SCENARIO = "controlled" + scenario;
                LLM_JSON = "/home/igor/IdeaProjects/sonarvsllm/sonarvsllm-testcases/src/main/resources/controlled/" + LLM_MODEL + "/" + CONTROLLED_SCENARIO + OUTPUT_JSON_SUFIX;
                assertCreated();
                CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + "controlled" + File.separator + scenario;
                evaluateFiles(BRANCHES.get(scenario));
            }

        }

        //        httpClient.close();
    }

    private static void assertCreated() {
        File outputFile = new File(LLM_JSON);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Carrega as classes para serem avaliadas do diretorio resources indicado como parâmetro
     *
     * @ActivateRequestContext Esta anotação é usada para indicar que podem ocorrer acesso base, mas sem transações com commit.
     */
    @ActivateRequestContext
    void evaluateFiles(String scenario) {
        try {
            // Path to the "classesToBeAnalysed" folder into "resources"
            Path dir = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(CLASS_FILES_TO_BE_ANALYSED)).toURI());
            try (Stream<Path> paths = Files.walk(dir)) {
                paths.forEach(path -> {
                    if (!path.toFile().isFile()) {
                        return;
                    }
//                    logger.warning("Loading " + path.toString() + " file");
                    StringBuilder javaClassFile = new StringBuilder();
                    // Opening the InputStream to the file
                    int lineCount = 0;
                    String classPackage = null;
                    try (InputStream inputStream = new FileInputStream(path.toFile())) {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                            String line;
                            javaClassFile = new StringBuilder();
                            classPackage = path.toString().split(CLASS_FILES_TO_BE_ANALYSED + "/")[1];
                            while ((line = reader.readLine()) != null) {
                                javaClassFile.append(line).append("\n");
                            }
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, e.getMessage(), e);
                    }

                    analyseClassFile(classPackage, javaClassFile, scenario);
                    //                    if (true) System.exit(-1);
                });
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        logger.warning("Encerrado análise qualitativa dos diffs");
    }

    private void analyseClassFile(String classPackage, StringBuilder javaClass, String scenario) {
        try {
            if (!new String(Files.readAllBytes(Paths.get(LLM_JSON))).contains(classPackage)) {
                requestLLM(classPackage, SYSTEM_PROMPT.toString(), USER_PROMPT_START, javaClass, USER_PROMPT_END, scenario);
            }
            else {
                logger.warning("Já analisado " + classPackage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método é responsável por enviar uma solicitação ao serviço de LLM com um command específico e um javaClass.
     * A resposta é então avaliada, processada e registrada.
     *
     * @param classAndPackage Java Class File Name
     * @param javaClass       The Class sourcecode to be analysed by GPT.
     * @param commandEnd      O final do command a ser enviado ao serviço GPT.
     * @param scenario
     */
    private void requestLLM(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, String scenario) {
        Map<String, Object> body = new HashMap<>();
//        ArrayList<Map<String, String>> messages = new ArrayList<>();
//        messages.add(Map.of("role", "user", "content", userPromptStart + javaClass + commandEnd));

            JsonArray messages = new JsonArray();
            JsonObject content = new JsonObject();
            content.put("role", "user");
            JsonArray parts = new JsonArray();
            JsonObject part = new JsonObject();
            part.put("text", userPromptStart + javaClass + commandEnd);
            parts.add(part);
            content.put("parts", parts);
            messages.add(content);

        body.put("contents", messages);
        JsonObject systemInstruction = new JsonObject();
        systemInstruction.put("role", "");
        parts = new JsonArray();
        part = new JsonObject();
        part.put("text", systemPrompt);
        parts.add(part);
        systemInstruction.put("parts", parts);

        //        messages.clear();
//        messages.add(Map.of("role", "system", "content", systemPrompt));
        body.put("systemInstruction", systemInstruction);

        JsonObject generationConfig = new JsonObject();
        generationConfig.put("temperature", 0);
        generationConfig.put("topP", 0.95);
        generationConfig.put("topK", 64);
//        generationConfig.put("candidateCount", your_candidateCount_here);
        generationConfig.put("maxOutputTokens", 120);
//        JsonArray stopSequences = new JsonArray();
//        stopSequences.add(your_stopSequence_here); // Adicione mais sequências de parada conforme necessário
//        generationConfig.put("stopSequences", stopSequences);
//        generationConfig.put("responseMimeType", "your_responseMimeType_here");

        body.put("generationConfig", generationConfig);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_LLM + "?key=" + apiKeyLLM)).header("Content-Type", "application/json")
                //                .header("Authorization", "Bearer " + apiKeyLLM)
                .POST(HttpRequest.BodyPublishers.ofString(new JsonObject(body).toString())).build();

        try {
            controleThreads.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<Void> responseEval = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> avaliaOcorrenciaDeErro(classAndPackage, systemPrompt, userPromptStart, javaClass, commandEnd, resp, scenario))
                .thenApply(stringHttpResponse -> parseGPTResponse(stringHttpResponse == null ? null : stringHttpResponse.body())).thenApply(response -> buildGTPResponse(classAndPackage, response, scenario))
                .thenAccept(diffGPTResponse -> registraAvaliacao(classAndPackage, diffGPTResponse));

        responseEval.join();
        controleThreads.release();
    }

    /**
     * Realizamos aqui o tratamento de todos os erros possíveis que a API do LLM costuma retornar.
     * <p>
     * Caso não exista erro, ou seja, o status code seja 200, o response é passado de forma íntegra como retorno da função.
     * <p>
     * Este método pode finalizar abruptamente o processo caso um erro imprevisto apareça.
     *
     * @param classAndPackage
     * @param javaClass
     * @param commandEnd
     * @param response        Retorno da API do LLM a ser avaliado na busca por erros
     * @param scenario
     * @return
     */
    private HttpResponse<String> avaliaOcorrenciaDeErro(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, HttpResponse<String> response, String scenario) {
        if (response.statusCode() == 429 || response.statusCode() == 500 || response.statusCode() == 503) {//Este erro ocorre quando atingimos o limite de chamadas por minuto da API da OpenAI
            try {
                logger.warning("Erro de excesso de requisições, realizando sleep para tentar novamente");
                Thread.sleep(100000);
//                Thread.sleep(60000);
                //Precisamos liberar um slot do controle de Threads aqui, pois o método abaixo irá tentar fazer acquire.
                controleThreads.release();
                requestLLM(classAndPackage, systemPrompt, userPromptStart, javaClass, commandEnd, scenario);
                return null;
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Erro ao Sleep", e);
            }
        } else if (response.statusCode() != 200) {//Para qualquer outro erro imprevisto nós vamos registrar os detalhes em log e encerrar a execução
            String command = systemPrompt + userPromptStart + javaClass.toString() + commandEnd;
            logger.log(Level.SEVERE, "StatusCode: " + response.statusCode());
            logger.log(Level.SEVERE, response.body());
            logger.log(Level.SEVERE, "Comando length " + command.length());
            logger.log(Level.SEVERE, command);
            controleThreads.release();
            System.exit(-1);
        }
        logger.log(Level.WARNING, "Realizado analise n° " + requestCount++ + " Response " + response.statusCode());
        return response;
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
    private GPTResponse buildGTPResponse(String classAndPackage, String response, String scenario) {
        if (response == null) {
            return null;
        }
        try {
            GPTResponse evaluation = OBJECT_MAPPER.readValue(response, new TypeReference<>() {});
            if (evaluation.score.endsWith("%")) {//Remove the % sign
                evaluation.score = evaluation.score.substring(0, evaluation.score.length() - 1);
            }
            evaluation.sonarData = sonarClient.getComponentMeasures(component + "%3A" + classAndPackage, scenario);
            System.out.println(evaluation);
            return evaluation;
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Error building DiffGPTResponse " + response, e);
            return null;
        }

    }

    /**
     * Este método faz o parse da response do GPT.
     * Ele extrai o texto da análise do chat do GPT e o número total de tokens usados, também insere o atributo do custo da chamada em tokens para ser salvo na base.
     * Este método também trata a response da API que pode ser abruptamente truncada, forçando o fechamento do json.
     *
     * @param response A response do GPT em formato de string.
     * @return O texto da análise do chat do GPT com o atributo do custo da chamada em tokens inserido. Retorna null se ocorrer um erro ao fazer o parse da response do GPT.
     */
    private String parseGPTResponse(String response) {
        if (response == null) {
            return null;
        }
        logger.warning("parseResponse " + response);
        try {
            JsonObject json = new JsonObject(response);
            String textGPTResponse = json.getJsonArray("candidates").getJsonObject(0).getJsonObject("content").getJsonArray("parts").getJsonObject(0).getString("text");
            if (textGPTResponse.contains("```json")) {
                textGPTResponse = textGPTResponse.replaceAll("```json", "").replaceAll("```", "");
            }
            logger.warning(json.getJsonObject("usageMetadata").toString());
            Integer tokensUsed = json.getJsonObject("usageMetadata").getInteger("totalTokenCount");

            totalTokensUsed += tokensUsed;
            logger.log(Level.WARNING, "Total tokens used so far: " + totalTokensUsed + " (R$ " + (totalTokensUsed * 0.005 / 1000) + ")");
            //Inserts the call cost attribute into tokens to be printed, as the first attribute of the JSON (Do not use ReplaceAll)
            textGPTResponse = textGPTResponse.replaceFirst("\\{", "\\{\"tokens\":\" " + tokensUsed + "\",");
            //This treatment is necessary because the API response can be abruptly truncated, so we have to force the json to close
            if (textGPTResponse.endsWith("\"}")) {
                return textGPTResponse;
            } else if (textGPTResponse.endsWith("\"")) {
                return textGPTResponse + "}";
            } else {
                return textGPTResponse + "\"}";
            }
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Erro ao fazer parse da response do GPT: " + response, e);
            return null;
        }
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
    private synchronized void registraAvaliacao(String fileName, GPTResponse evaluation) {
        try {
            if (evaluation != null) {
                logger.warning("Evaluation result for " + fileName + ": \n" + evaluation);
                Files.write(Paths.get(LLM_JSON), (evaluation + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, fileName + ": " + evaluation, e);
        }
    }
}