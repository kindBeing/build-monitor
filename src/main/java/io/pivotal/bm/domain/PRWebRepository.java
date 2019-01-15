package io.pivotal.bm.domain;

import io.pivotal.bm.models.PREntry;

import java.util.List;

public interface PRWebRepository {
    List<PREntry> fetch(List<PREntry> oldPREntries);
}
