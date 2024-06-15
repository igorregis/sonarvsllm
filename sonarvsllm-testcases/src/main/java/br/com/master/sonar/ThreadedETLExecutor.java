package br.com.master.sonar;

import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadedETLExecutor {

    protected Logger logger;

    protected Vector<Thread> threads = new Vector<>();

    protected Vector<String> threadsCounter = new Vector<>();

    @ConfigProperty(name = "quarkus.datasource.jdbc.min-size")
    protected int threadNumber;

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
}
