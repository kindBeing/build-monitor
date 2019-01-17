package io.pivotal.bm.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivotal.bm.URLProvider;
import io.pivotal.bm.domain.RepoWebRepository;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

public class RepoWebService implements RepoWebRepository {
    private RestTemplate restTemplate;
    private URLProvider urlProvider;

    public RepoWebService(RestTemplateBuilder restTemplateBuilder, URLProvider urlProvider) {
        restTemplate = restTemplateBuilder.build();
        this.urlProvider = urlProvider;
    }

    public RepoInfo fetch() {
        RepoInfo repoInfo = new RepoInfo();
        CompletableFuture<JsonNode> compareFuture = get(urlProvider.getCompareURL());
        fetchBuildData().thenAcceptBoth(compareFuture, (buildData, compareData) -> {
            repoInfo.setStatus(compareData.get("status").asText());
            repoInfo.setCountCommitDifference(compareData.get("total_commits").asInt());
            repoInfo.setBuildState(buildData.get("state").asText());
        });
        return repoInfo;
    }

    private CompletableFuture<JsonNode> fetchBuildData() {
        CompletableFuture<JsonNode> branchFuture = get(urlProvider.getBranchURL());
        CompletableFuture<JsonNode> buildFuture = branchFuture.thenCompose(branchData -> {
            JsonNode commitData = branchData.get("commit");
            String sha = commitData.get("sha").asText();
            return get(urlProvider.getBuildURL(sha));
        });
        return buildFuture;
    }

    private CompletableFuture<JsonNode> get(String url) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(url, JsonNode.class));
    }
}
