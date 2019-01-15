package io.pivotal.bm.managers;

import io.pivotal.bm.domain.DataManager;
import io.pivotal.bm.domain.RepoDBRepository;
import io.pivotal.bm.domain.RepoWebRepository;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RepoDataManager implements DataManager {

    private RepoDBRepository repository;
    private RepoWebRepository webRepository;


    public RepoDataManager(RepoDBRepository repository, RepoWebRepository webRepository) {
        this.repository = repository;
        this.webRepository = webRepository;
    }

    @Override
    @Async
    public void manage() {
        RepoInfo repoInfo = webRepository.fetch();
        repository.create(repoInfo); // TODO: If not present.
    }
}
