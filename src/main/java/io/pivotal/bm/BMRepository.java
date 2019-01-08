package io.pivotal.bm;
import io.pivotal.bm.models.PREntry;
import io.pivotal.bm.models.RepoInfo;

import java.util.List;

public interface BMRepository {
    void create(PREntry entry);

    void createRepo(RepoInfo repoInfo);

    void create(Iterable<PREntry> entries);

    void update(PREntry entry);

    void remove(PREntry entry);

    int countUnmerged();

    int countUnknown();

    RepoInfo getRepoInfo();

    List<PREntry> list();

    List<PREntry> listUnmerged();
}
