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


    public List<PREntry> getPrEntries() {
        return prEntries;
    }

    public void setPrEntries(List<PREntry> prEntries) {
        this.prEntries = prEntries;
    }

    public PRStats getPrStats() {
        return prStats;
    }

    public void setPrStats(PRStats prStats) {
        this.prStats = prStats;
    }

    public RepoInfo getRepoInfo() {
        return repoInfo;
    }

    public void setRepoInfo(RepoInfo repoInfo) {
        this.repoInfo = repoInfo;
    }
}
