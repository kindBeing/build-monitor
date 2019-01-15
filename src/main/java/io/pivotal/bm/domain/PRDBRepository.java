package io.pivotal.bm.domain;

import io.pivotal.bm.models.PREntry;

import java.util.List;

public interface PRDBRepository {
    void create(PREntry entry);
    int countUnmerged();
    int countUnknown();
    List<PREntry> list();
    List<PREntry> listUnmerged();
    void updateUnmerged(List<PREntry> newPREntries);
}
