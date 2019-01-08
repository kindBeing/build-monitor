package io.pivotal.bm.git;

import io.pivotal.bm.BMRepository;
import io.pivotal.bm.URLProvider;
import io.pivotal.bm.models.RepoInfo;
import io.pivotal.bm.services.GitDataService;

import java.util.concurrent.ExecutionException;

public class RepoManager {
    private BMRepository bmRepository;
    private GitDataService gitDataService;
    private URLProvider urlProvider;


    public RepoManager(BMRepository bmRepository, GitDataService gitDataService, URLProvider urlProvider) {
        this.bmRepository = bmRepository;
        this.gitDataService = gitDataService;
        this.urlProvider = urlProvider;
    }

    public void manage() throws ExecutionException, InterruptedException {
        RepoInfo repoInfo = gitDataService.fetchRepoInfo();
        bmRepository.createRepo(repoInfo);
    }
}
