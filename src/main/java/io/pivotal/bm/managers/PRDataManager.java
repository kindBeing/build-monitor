package io.pivotal.bm.managers;

import io.pivotal.bm.domain.DataManager;
import io.pivotal.bm.domain.PRDBRepository;
import io.pivotal.bm.domain.PRWebRepository;
import io.pivotal.bm.models.PREntry;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class PRDataManager implements DataManager {
    private PRDBRepository repository;
    private PRWebRepository webRepository;

    public PRDataManager(PRDBRepository repository, PRWebRepository webRepository) {
        this.repository = repository;
        this.webRepository = webRepository;
    }

    @Override
    @Async
    public void manage() {
        List<PREntry> oldPREntries = repository.listUnmerged();

        List<PREntry> newPREntries = webRepository.fetch(oldPREntries);

        repository.updateUnmerged(newPREntries);
    }
}
