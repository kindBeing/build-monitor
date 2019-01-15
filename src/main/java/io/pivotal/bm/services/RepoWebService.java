package io.pivotal.bm.services;

import io.pivotal.bm.URLProvider;
import io.pivotal.bm.domain.RepoWebRepository;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

public class RepoWebService implements RepoWebRepository {
    private RestTemplate restTemplate;
    private URLProvider urlProvider;

    public RepoWebService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        this.urlProvider = new URLProvider();
    }

    public CompletableFuture<String> fetch(String url) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(url, String.class));
    }

    public RepoInfo fetch() {
        // fetch the repo info object. build a new RepoInfo and ship

        getData();
//        repoInfo = new RepoInfo();
        return null;
    }

    private CompletableFuture<String> getData() {
        return buildData().thenCombine(compareData(), (buildData, compareData) -> {
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
}
//    String status = ""; //TODO: compareData.status;
//    String commitDiffCount = ""; //TODO compareData.total_commits;
//    String buildState = ""; // TODO: buildData.state;
//    String buildColor = ""; // TODO: resolver
