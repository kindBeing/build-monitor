package io.pivotal.bm.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivotal.bm.URLProvider;
import io.pivotal.bm.domain.PRWebRepository;
import io.pivotal.bm.models.PREntry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PRWebService implements PRWebRepository {
    private URLProvider urlProvider;
    private RestTemplate restTemplate;
    private String UNKNOWN_STATUS = "UNKNOWN_STATUS";


    PRWebService(RestTemplateBuilder restTemplateBuilder, URLProvider urlProvider) {
        restTemplate = restTemplateBuilder.build();
        this.urlProvider = urlProvider;
    }

    @Override
    public List<PREntry> fetch(List<PREntry> entries) {
        for(PREntry entry: entries) {
            String url = urlProvider.getPullURL(entry.getPrId());
            entry.setStatus(getStatus(url));
        }
        return entries;
    }

    private String getStatus(String url) {
        final String[] status = {UNKNOWN_STATUS};
        get(url).thenAccept(prData -> {
            status[0] = prData.get("status").asText();
        });
        return status[0];
    }


    public CompletableFuture<JsonNode> get(String url) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(url, JsonNode.class));
    }
}
