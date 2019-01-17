package io.pivotal.bm.managers;

import io.pivotal.bm.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

//@Component
public class DisplayDataManager {
    private static final long delay  = 1000L;
    private static final long period = 60000L;

    private DataManager prDataManager;
    private DataManager repoManager;


    public DisplayDataManager(RepoDBRepository repoDBRepository,
                              PRDBRepository prdbRepository,
                              RepoWebRepository repoWebRepository,
                              PRWebRepository prWebRepository) {
        this.repoManager = new RepoDataManager(repoDBRepository, repoWebRepository);
        this.prDataManager = new PRDataManager(prdbRepository, prWebRepository);
    }

    @Scheduled(initialDelay=delay, fixedRate=period)
    public void manage() throws ExecutionException, InterruptedException {
        repoManager.manage();
        prDataManager.manage();
    }
}
