package science.com.master.sonar.llmvshuman;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.jetbrains.annotations.NotNull;
import science.com.master.sonar.GPTResponse;

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
@Named("SourceCodeLLMAnthropicClaudeLLMvsHuman")
public class SourceCodeLLMAnthropicClaude extends science.com.master.sonar.SourceCodeLLMAnthropicClaude {

    protected String PAPER = "llmvshuman";

    public SourceCodeLLMAnthropicClaude() {
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