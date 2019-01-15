package io.pivotal.bm.domain;

import java.util.concurrent.ExecutionException;

public interface DataManager {
    void manage() throws ExecutionException, InterruptedException;
}
