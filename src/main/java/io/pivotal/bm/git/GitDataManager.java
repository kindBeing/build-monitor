package io.pivotal.bm.git;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class GitDataManager {
    private static final long delay  = 1000L;
    private static final long period = 60000L;

    private PRManager prManager;
    private RepoManager repoManager;

    public GitDataManager(RepoManager repoManager, PRManager prManager) {
        this.repoManager = repoManager;
        this.prManager = prManager;
    }

    @Scheduled(initialDelay=delay, fixedRate=period)
    public void manage() throws ExecutionException, InterruptedException {
        repoManager.manage();
        prManager.manage();
    }
}
