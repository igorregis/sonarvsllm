package science.com.master.sonar.llmvshuman;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;
import science.com.master.sonar.GPTResponse;
import science.com.master.sonar.ThreadedETLExecutor;

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
 */
@ApplicationScoped
@Named("SourceCodeLLMGoogleGeminiLLMvsHuman")
public class SourceCodeLLMGoogleGemini extends science.com.master.sonar.SourceCodeLLMGoogleGemini {

    protected String PAPER = "llmvshuman";

    public SourceCodeLLMGoogleGemini() {
        SYSTEM_PROMPT.setLength(0);
        SYSTEM_PROMPT.append("The assistant is a seasoned senior software engineer, with deep Java Language expertise and a native Portuguese speaker, ");
        SYSTEM_PROMPT.append("doing source code evaluation as part of a due diligence process, these source code are presented in the form of a Java Code Snipet. ");
        SYSTEM_PROMPT.append("Your task is to assign a score from 0 to 10 based on the readability level and overall ease of comprehension.\n");
        SYSTEM_PROMPT.append("Your answers MUST be presented ONLY in the following json format: {\"score\":\"N\", \"reasoning\":\"your explanation about the score\" } ");
        SYSTEM_PROMPT.append("- The \"explanation\" attribute must not surpass 450 characters and MUST NOT contain especial characters or new lines\n");

        USER_PROMPT_START = "Evaluate the following Java source code: ";
        USER_PROMPT_END = "This is the end of the Code Snipet, the assistant should present your json answer:";
    }

    @Override
    protected String @NotNull [] getScenarios() {
        return new String[]{SCENARIO_ORIGINAL, SCENARIO_NO_COMMENTS, SCENARIO_BAD_NAMES};
    }

    @Override
    protected String getPaper() {
        return PAPER;
    }

    @Override
    protected void decorateData(String classAndPackage, String scenario, GPTResponse evaluation) {
        evaluation.name = classAndPackage;
    }
}