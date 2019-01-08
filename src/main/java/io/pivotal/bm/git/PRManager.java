package io.pivotal.bm.git;

import io.pivotal.bm.BMRepository;
import io.pivotal.bm.URLProvider;
import io.pivotal.bm.models.PREntry;
import io.pivotal.bm.services.GitDataService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PRManager {
    private BMRepository bmRepository;
    private GitDataService gitDataService;
    private URLProvider urlProvider;

    public PRManager(BMRepository bmRepository, GitDataService gitDataService, URLProvider urlProvider) {
        this.bmRepository = bmRepository;
        this.gitDataService = gitDataService;
        this.urlProvider = urlProvider;
    }

    public void manage() throws ExecutionException, InterruptedException {
        Map<String, String> prFutures = new HashMap<>();

        List<PREntry> prEntries = bmRepository.listUnmerged();

        for(PREntry entry: prEntries){
            CompletableFuture<String> future = gitDataService.fetchData(urlProvider.getPullURL(entry.getPrId()));
            prFutures.put(entry.getPrId(), future.get());
        }

        Iterator it = prFutures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
//            TODO:
//            System.out.println(pair.getKey() + " = " + pair.getValue());
//            bmRepository.update();
        }
    }
}
