package io.pivotal.bm.domain;

import io.pivotal.bm.models.RepoInfo;

public interface RepoWebRepository {
    RepoInfo fetch();
}
