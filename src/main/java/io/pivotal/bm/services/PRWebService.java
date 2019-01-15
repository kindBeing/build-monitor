package io.pivotal.bm.services;

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
    private RestTemplate restTemplate;
    private URLProvider urlProvider;

    public PRWebService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        this.urlProvider = new URLProvider();
    }

    public CompletableFuture<String> fetch(String url) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(url, String.class));
    }

    private List<PREntry> getData(List<PREntry> oldPREntries) {
        for(PREntry entry: oldPREntries) {
            String url = urlProvider.getPullURL(entry.getPrId());
            fetch(url); // incoming data. update oldPREntries or create new list.
        }

        return null;
    }

    @Override
    public List<PREntry> fetch(List<PREntry> oldPREntries) {
        return getData(oldPREntries);
    }
}
