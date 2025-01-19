package science.com.master.sonar;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
 *
 *
 * Selected Files from Quarkus Project with less tham 800 lines of code:
 *
 * Executar de dentro da pasta core do projeto:
 */
@ApplicationScoped
public class SourceCodeLLM extends ThreadedETLExecutor {


    /**
     * URL da API do LLM usada para chamadas
     */
    public static final String URL_LLM = "https://api.openai.com/v1/chat/completions";

    /**
     * Parâmetro indicando o prompt de comando enviado ao LLM
     */
    public static final String PROMPT = "prompt";

    /**
     * Parâmetro que indica o número máximo de tokens que o LLM deve usar para produzir sua resposta
     */
    public static final String MAX_TOKENS = "max_tokens";

    /**
     * Parâmetro de temperatura, usado para determinar o quanto criativo ou determinístico será o LLM, quanto menor mais determinístico
     */
    public static final String TEMPERATURE = "temperature";

    public static final String FREQUENCY_PENALTY = "frequency_penalty";

    public static final String PRESENCE_PENALTY = "presence_penalty";

    public static final String TOP_P = "top_p";

    public static final String N_PARAM = "n";

    public static final String STOP_SIGN = "stop";

    /**
     * Marcador de final de comando para o LLM, usado no header por exemplo para que o LLM saiba que a própria mensagem chegou ao fim
     */
    public static final String IM_END = "<|im_end|>";

    public static final int TAMANHO_MAXIMO_ACEITAVEL_PRA_DIFFS = 28000;

    /**
     * Número máximo de Threads a serem disparadas para realizar chamadas simultâneas ao Chat GPT
     */
    public static final int THREADS_GPT = 10;

//    public static final String CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + "shattered-pixel-dungeon";
    public static final String CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + "quarkus";
//    public static final String CLASS_FILES_TO_BE_ANALYSED = "classFilesToBeAnalysed" + File.separator + "controlled";

//    public static final String component = "ismvru_shattered-pixel-dungeon";
    public static final String component = "quarkusio_quarkus";
//        public static final String component = "igorregis_sonarvsllm";
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

    @ConfigProperty(name = "api.key.llm")
    private String apiKeyLLM;

    /**
     * Contador de requests, usamos ele para registrar no log a quantidade de análises realizadas
     */
    private int requestCount;

    /**
     * Contador de tokens usados, usamos ele para saber quanto estamos gastando com as análises, em tempo de execução
     */
    private Integer totalTokensUsed = 0;

    public SourceCodeLLM() {
        SYSTEM_PROMPT.append("<|im_start|>system\n").append("The assistant is a seasoned senior software engineer, with deep Java Language expertise, ");
        SYSTEM_PROMPT.append("doing source code evaluation as part of a due diligence process, these source code are presented in the form of a Java Class File. ");
        SYSTEM_PROMPT.append("Your task is to emit a score from 0 to 100 based on the readability level and overall quality of the source code presented.\n");
        SYSTEM_PROMPT.append("Your answers MUST be presented ONLY in the following json format: {\"score\":\"NN%\", reasoning:\"your explanation about the score\" } ");
        SYSTEM_PROMPT.append("- The \"explanation\" attribute must not surpass 450 characters and MUST NOT contain especial characters or new lines <|im_end|>\n");

        USER_PROMPT_START = "<|im_start|>user: Evaluate the following Java source code: ";
        USER_PROMPT_END = "This is the end of the classh file, the assistant should present your json answer:<|im_end|><|im_start|>assistant";

        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    }

    @ActivateRequestContext
    public void run() {
        setupHttpClient();
//        correcao();
        evaluateFiles(false, null);
//        httpClient.close();
    }

    @Override
    protected String @NotNull [] getScenarios() {
        return new String[0];
    }

    /**
     *  Carrega as classes para serem avaliadas do diretorio resources indicado como parâmetro
     * @ActivateRequestContext Esta anotação é usada para indicar que podem ocorrer acesso base, mas sem transações com commit.
     */
    @ActivateRequestContext
    protected void evaluateFiles(boolean createInputBatch, String scenario) {
        try {
            // Path to the "classesToBeAnalysed" folder into "resources"
            Path dir = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(CLASS_FILES_TO_BE_ANALYSED)).toURI());
            try (Stream<Path> paths = Files.walk(dir)) {
                paths.forEach(path -> {
                    if (!path.toFile().isFile()) {
                        return;
                    }
                    logger.warning("Loading " + path.toString() + " file");
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
                    logger.warning("Analisando " + classPackage);
                    if (doesComponentExists(classPackage))
                        analyseClassFile(classPackage, javaClassFile, createInputBatch);
                });
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        logger.warning("Encerrado análise qualitativa dos diffs");
    }

    /**
     * Verifica se o componente existe no Sonarcloud, pois estamos trabalhando com versões antigas dos fontes
     * @param classAndPackage
     * @return
     */
    private boolean doesComponentExists(String classAndPackage) {
        try {
            sonarClient.getComponentMeasures(component + "%3A" + classAndPackage, "main");
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    private void analyseClassFile(String classPackage, StringBuilder javaClass, boolean createInputBatch) {
        requestChatGPT(classPackage ,SYSTEM_PROMPT + USER_PROMPT_START, javaClass, USER_PROMPT_END,createInputBatch);
    }


    /**
     * Este método é responsável por enviar uma solicitação ao serviço de LLM com um command específico e um javaClass.
     * A resposta é então avaliada, processada e registrada.
     *
     * @param classAndPackage     Java Class File Name
     * @param command      O command específico a ser enviado ao serviço GPT.
     * @param javaClass    The Class sourcecode to be analysed by GPT.
     * @param commandEnd   O final do command a ser enviado ao serviço GPT.
     */
    protected void requestChatGPT(String classAndPackage, String command, StringBuilder javaClass, String commandEnd, boolean createInputBatch) {

        Map<String, Object> body = new HashMap<>();
        body.put(PROMPT, command + javaClass + commandEnd);
        body.put(MAX_TOKENS, 120);//Max length request + response: 8193
        body.put(TEMPERATURE, 0);
        body.put(FREQUENCY_PENALTY, 0);
        body.put(PRESENCE_PENALTY, 0);
        body.put(TOP_P, 0.5);
        body.put(N_PARAM, 1);
        body.put(STOP_SIGN, IM_END);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_LLM)).header("Content-Type", "application/json").header("api-key", apiKeyLLM)
                .POST(HttpRequest.BodyPublishers.ofString(new JsonObject(body).toString())).build();

        try {
            controleThreads.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CompletableFuture<Void> responseEval = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> avaliaOcorrenciaDeErro(classAndPackage,command, null, javaClass, commandEnd,resp, null, createInputBatch))
                .thenApply(stringHttpResponse -> parseGPTResponse(stringHttpResponse == null ? null : stringHttpResponse.body())).thenApply(response -> buildGTPResponse(classAndPackage, response, null))
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
     * @param command           command enviado ao LLM
     * @param javaClass
     * @param commandEnd
     * @param response          Retorno da API do LLM a ser avaliado na busca por erros
     * @return
     */
    protected HttpResponse<String> avaliaOcorrenciaDeErro(String classAndPackage, String command, String userPrompt, StringBuilder javaClass, String commandEnd, HttpResponse<String> response, String scenario, boolean createInputBatch) {
        if (response.statusCode() == 429) {//Este erro ocorre quando atingimos o limite de chamadas por minuto da API da Azure
            try {
                logger.warning("Erro de excesso de requisições, realizando sleep para tentar novamente");
                Thread.sleep(5000);
                //Precisamos liberar um slot do controle de Threads aqui, pois o método abaixo irá tentar fazer acquire.
                controleThreads.release();
                requestChatGPT(classAndPackage, command, javaClass, commandEnd, createInputBatch);
                return null;
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Erro ao Sleep", e);
            }
        } else if (response.statusCode() != 200) {//Para qualquer outro erro imprevisto nós vamos registrar os detalhes em log e encerrar a execução
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
     * Este método faz o parse da response do GPT.
     * Ele extrai o texto da análise do chat do GPT e o número total de tokens usados, também insere o atributo do custo da chamada em tokens para ser salvo na base.
     * Este método também trata a response da API que pode ser abruptamente truncada, forçando o fechamento do json.
     *
     * @param response A response do GPT em formato de string.
     * @return O texto da análise do chat do GPT com o atributo do custo da chamada em tokens inserido. Retorna null se ocorrer um erro ao fazer o parse da response do GPT.
     */
    protected String parseGPTResponse(String response) {
        if (response == null) {
            return null;
        }
        logger.warning("parseResponse " + response);
        try {
            JsonObject json = new JsonObject(response);
            String textGPTResponse = json.getJsonArray("choices").getJsonObject(0).getString("text");
            logger.warning(json.getJsonObject("usage").toString());
            Integer tokensUsed = json.getJsonObject("usage").getInteger("total_tokens");
            totalTokensUsed += tokensUsed;
            logger.log(Level.WARNING, "Total tokens used so far: " + totalTokensUsed + " (R$ " + (totalTokensUsed * 0.0097 / 1000) + ")");
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

    /**%
     * Este método registra a avaliação do GPT no banco de dados
     * Ele inicia uma transação, encontra o CommitDiff correspondente no banco de dados e atualiza seus campos com os valores da avaliação.
     * Em seguida, persiste as alterações e faz o commit da transação.
     * Se ocorrer um erro durante esse processo, ele é registrado e a thread é liberada.
     *
     * @param fileName  Java Class File Name
     * @param evaluation A avaliação do GPT para o CommitDiff.
     */
    protected synchronized void registraAvaliacao(String fileName, GPTResponse evaluation) {
        try {
            if (evaluation != null) {
                logger.warning("Evaluation result for " + fileName+ ": \n" + evaluation);
                Files.write(Paths.get("/home/igor/IdeaProjects/sonarvsllm/sonarvsllm-testcases/src/main/resources/sonarAndLLM.json"),
                        (evaluation + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, fileName+ ": " + evaluation, e);
        }
    }

    @Override
    protected String getLLMModel() {
        return "GPT35";
    }

    @Override
    protected String getPaper() {
        return PAPER;
    }
}
