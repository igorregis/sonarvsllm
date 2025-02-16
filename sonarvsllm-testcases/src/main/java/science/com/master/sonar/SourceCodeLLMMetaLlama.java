package science.com.master.sonar;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
@Named("SourceCodeLLMMetaLlamaControlled")
public class SourceCodeLLMMetaLlama extends ThreadedETLExecutor {

    /**
     * URL da API do LLM usada para chamadas
     */
    public static final String URL_LLM = "https://api.deepinfra.com/v1/openai/chat/completions";

    /**
     * Parâmetro indicando o prompt de comando enviado ao LLM
     */
    public static final String MESSAGES = "messages";

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

    public static final String LLM_MODEL = "Llama31-405b";
//    public static final String LLM_MODEL = "Llama31-8b";

    private final Map<String, String> MODEL_PARAM = Map.of("Llama31-405b", "meta-llama/Meta-Llama-3.1-405B-Instruct",
                                                            "Llama31-8b", "meta-llama/Meta-Llama-3.1-8B-Instruct");

    /**
     * System prompt enviado ao LLM
     */
    protected final StringBuilder SYSTEM_PROMPT = new StringBuilder();

    @ConfigProperty(name = "api.key.deep")
    private String apiKeyLLM;

    public SourceCodeLLMMetaLlama() {
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

    @Override
    protected String @NotNull [] getScenarios() {
        return new String[]{SCENARIO_ORIGINAL, SCENARIO_NO_COMMENTS, SCENARIO_BAD_NAMES, SCENARIO_BAD_NAMES_NO_COMMENTS, SCENARIO_CLEAN_CODE,
                SCENARIO_BUSE_AND_WEIMER, SCENARIO_DORN, SCENARIO_SCALABRINO};
    }

    /**
     * Carrega as classes para serem avaliadas do diretorio resources indicado como parâmetro
     *
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

                    logger.warning("Analisando " + classPackage);
                    analyseClassFile(classPackage, javaClassFile, scenario, createInputBatch);
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

    private void analyseClassFile(String classPackage, StringBuilder javaClass, String scenario, boolean createInputBatch) {
        try {
            if (!new String(Files.readAllBytes(Paths.get(LLM_JSON))).contains(classPackage)) {
                requestLLM(classPackage, SYSTEM_PROMPT.toString(), USER_PROMPT_START, javaClass, USER_PROMPT_END, scenario, createInputBatch);
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
    private void requestLLM(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, String scenario, boolean createInputBatch) {
        Map<String, Object> body = new HashMap<>();
        MessageWrapper messagens = new MessageWrapper();
        messagens.setMessages(new ArrayList<>(2));
        messagens.getMessages().add(new MessageWrapper.Message("system", systemPrompt));
        messagens.getMessages().add(new MessageWrapper.Message("user", userPromptStart + javaClass + commandEnd));
        String command = messagens.toString();
        body.put(MESSAGES, messagens.getMessages());
        body.put(MAX_TOKENS, 120);//Max length request + response: 8193
        body.put(TEMPERATURE, 0);
        body.put("model", MODEL_PARAM.get(LLM_MODEL));
        body.put(FREQUENCY_PENALTY, 0);
        body.put(PRESENCE_PENALTY, 0);
        body.put(TOP_P, 0.5);
        body.put(N_PARAM, 1);
        body.put(STOP_SIGN, IM_END);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_LLM)).header("Content-Type", "application/json").header("Authorization", "Bearer " + apiKeyLLM)
                .POST(HttpRequest.BodyPublishers.ofString(new JsonObject(body).toString())).build();

        processLLMResponse(classAndPackage, systemPrompt, userPromptStart, javaClass, commandEnd, scenario, createInputBatch, request);
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
    protected HttpResponse<String> avaliaOcorrenciaDeErro(String classAndPackage, String systemPrompt, String userPromptStart, StringBuilder javaClass, String commandEnd, HttpResponse<String> response, String scenario, boolean createInputBatch) {
        if (response.statusCode() == 429 || response.statusCode() == 500 || response.statusCode() == 503) {//Este erro ocorre quando atingimos o limite de chamadas por minuto da API da OpenAI
            try {
                logger.warning("Erro de excesso de requisições, realizando sleep para tentar novamente");
                Thread.sleep(100000);
//                Thread.sleep(60000);
                //Precisamos liberar um slot do controle de Threads aqui, pois o método abaixo irá tentar fazer acquire.
                controleThreads.release();
                requestLLM(classAndPackage, systemPrompt, userPromptStart, javaClass, commandEnd, scenario, createInputBatch);
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
            String textGPTResponse = json.getJsonArray("choices").getJsonObject(0).getJsonObject("message").getString("content");
            if (textGPTResponse.contains("```json")) {
                textGPTResponse = textGPTResponse.replaceAll("```json", "").replaceAll("```","");
            }
            logger.warning(json.getJsonObject("usage").toString());
            Integer tokensUsed = json.getJsonObject("usage").getInteger("total_tokens");
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

    @Override
    protected String getLLMModel() {
        return LLM_MODEL;
    }

    @Override
    protected String getPaper() {
        return PAPER;
    }
}