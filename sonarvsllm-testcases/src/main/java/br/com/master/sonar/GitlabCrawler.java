package br.com.master.sonar;

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
public class GitlabCrawler implements QuarkusApplication {


    @Inject
    private SourceCodeLLM sourceCodeLLM;
    @Inject
    private SourceCodeLLM4o sourceCodeLLM4o;
    @Inject
    private SourceCodeLLMGoogleGemini sourceCodeLLMGoogleGemini;

    private Logger logger;

    public GitlabCrawler() {
    }

    @PostConstruct
    void init() {
        logger = Logger.getLogger(this.getClass().getName());
        logger.warning("Iniciando programa");
    }

    @Override
    public int run(String... args) throws Exception {
//        sourceCodeLLMGoogleGemini.run();
        sourceCodeLLM4o.run();
        return 0;
    }
}

