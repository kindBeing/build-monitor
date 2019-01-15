package io.pivotal.bm.domain;

import io.pivotal.bm.models.RepoInfo;

public interface RepoDBRepository {
    void create(RepoInfo repoInfo);
    RepoInfo getRepoInfo();
}
