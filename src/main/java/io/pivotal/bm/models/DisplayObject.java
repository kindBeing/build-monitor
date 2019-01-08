package io.pivotal.bm.models;

import java.util.List;

public class DisplayObject {
    private List<PREntry> prEntries;
    private PRStats prStats;
    private RepoInfo repoInfo;

    public DisplayObject(List<PREntry> prEntries, PRStats prStats, RepoInfo repoInfo) {
        this.prEntries = prEntries;
        this.prStats = prStats;
        this.repoInfo = repoInfo;
    }
}
