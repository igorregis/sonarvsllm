package science.com.master.sonar;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

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
    private SourceCodeLLM4o sourceCodeLLM4o;
    @Inject
    private SourceCodeLLMGoogleGemini sourceCodeLLMGoogleGemini;
    @Inject
    private SourceCodeLLMMetaLlama sourceCodeLLMMetaLlama;
    @Inject
    private SourceCodeLLMAnthropicClaude sourceCodeLLMAnthropicClaude;

    @Inject
    private science.com.master.sonar.llmvshuman.SourceCodeLLM4o sourceCodeLLMvsHuman;
    @Inject
    private science.com.master.sonar.llmvshuman.SourceCodeLLM4o sourceCodeLLM4ovsHuman;
    @Inject
    private science.com.master.sonar.llmvshuman.SourceCodeLLMGoogleGemini sourceCodeLLMGoogleGeminivsHuman;
    @Inject
    private science.com.master.sonar.llmvshuman.SourceCodeLLMMetaLlama sourceCodeLLMMetaLlamavsHuman;
    @Inject
    private science.com.master.sonar.llmvshuman.SourceCodeLLMAnthropicClaude sourceCodeLLMAnthropicClaudevsHuman;

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
        sourceCodeLLMGoogleGemini.run();
//        sourceCodeLLM4o.run();
//        sourceCodeLLMMetaLlama.run();
//        sourceCodeLLMAnthropicClaude.run();
        return 0;
    }
}

