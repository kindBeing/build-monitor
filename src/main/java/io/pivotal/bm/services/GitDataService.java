package io.pivotal.bm.services;

import io.pivotal.bm.URLProvider;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitDataService {
    private RestTemplate restTemplate;
    private URLProvider urlProvider;
    private RepoInfo repoInfo;

    public GitDataService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        repoInfo = new RepoInfo();
    }

    public RepoInfo fetchRepoInfo() {
        return repoInfo;
    }

    private void fetchData() {
        buildData().thenCombine(compareData(), (buildData, compareData) -> {
            // use buildData and compareData to form RepoInfo object to use the data to form RepoInfo Object
            return buildData; // Could be anything.
        });
    }

    private CompletableFuture<String> buildData() {
        CompletableFuture<String> future = fetch(urlProvider.getBranchURL()).thenCompose(
                json_response -> {
                    System.out.println("Extract branch SHA");
                    return fetch(urlProvider.getBuildURL("sha"));
                });
        return future;
    }

    private CompletableFuture<String> compareData() {
        return fetch(urlProvider.getCompareURL());
    }

    @Async
    public CompletableFuture<String> fetch(String url) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(url, String.class));
    }
}
//
//    String status = ""; //TODO: compareData.status;
//    String commitDiffCount = ""; //TODO compareData.total_commits;
//    String buildState = ""; // TODO: buildData.state;
//    String buildColor = ""; // TODO: resolver
