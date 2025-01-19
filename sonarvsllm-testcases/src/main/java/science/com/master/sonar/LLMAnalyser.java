package science.com.master.sonar;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

/**
 * A classe {@code GitlabCrawler} implementa a interface {@code QuarkusApplication}, portanto eh executada assim que o processo quarkus sobe
 * Ela usa a API do GitLab para obter eventos de usuários e conta o número de eventos `PUSHED` que um usuário realizou em um ano.
 * A aplicação carrega uma lista de desenvolvedores de um arquivo CSV chamado `chaves.csv` e cria uma thread para cada desenvolvedor para buscar seus eventos.
 * Depois de todas as threads terem concluído, a aplicação imprime o número total de eventos `PUSHED` para cada desenvolvedor.
 *
 * @author f4353008
 */
@QuarkusMain(name = "stats")
public class LLMAnalyser implements QuarkusApplication {


    @Inject
    private SourceCodeLLM sourceCodeLLM;
    @Inject
    @Named("SourceCodeLLM4oControlled")
    private SourceCodeLLM4o sourceCodeLLM4o;
    @Inject
    @Named("SourceCodeLLMGoogleGeminiControlled")
    private SourceCodeLLMGoogleGemini sourceCodeLLMGoogleGemini;
    @Inject
    @Named("SourceCodeLLMMetaLlamaControlled")
    private SourceCodeLLMMetaLlama sourceCodeLLMMetaLlama;
    @Inject
    @Named("SourceCodeLLMAnthropicClaudeControlled")
    private SourceCodeLLMAnthropicClaude sourceCodeLLMAnthropicClaude;

    @Inject
    @Named("SourceCodeLLMGoogleGeminiLLMvsHuman")
    private science.com.master.sonar.llmvshuman.SourceCodeLLMGoogleGemini sourceCodeLLMGoogleGeminivsHuman;

    @Inject
    @Named("SourceCodeLLM4oLLMvsHuman")
    private science.com.master.sonar.llmvshuman.SourceCodeLLM4o sourceCodeLLM4ovsHuman;

    @Inject
    @Named("SourceCodeLLMAnthropicClaudeLLMvsHuman")
    private science.com.master.sonar.llmvshuman.SourceCodeLLMAnthropicClaude sourceCodeLLMAnthropicClaudevsHuman;

    @Inject
    @Named("SourceCodeLLMMetaLlamaLLMvsHuman")
    private science.com.master.sonar.llmvshuman.SourceCodeLLMMetaLlama sourceCodeLLMMetaLlamavsHuman;

    private Logger logger;

    public LLMAnalyser() {
    }

    @PostConstruct
    void init() {
        logger = Logger.getLogger(this.getClass().getName());
        logger.warning("Iniciando programa");
    }

    @Override
    public int run(String... args) throws Exception {
//        sourceCodeLLMGoogleGemini.run(false);
//            sourceCodeLLM4o.run(true);
//        sourceCodeLLMAnthropicClaude.run(false);
//        sourceCodeLLMMetaLlama.run(false);


        sourceCodeLLMGoogleGeminivsHuman.run(false);
//        sourceCodeLLM4ovsHuman.run(true);
//        sourceCodeLLMMetaLlamavsHuman.run(false);
//        sourceCodeLLMAnthropicClaudevsHuman.run(true);
        return 0;
    }
}

