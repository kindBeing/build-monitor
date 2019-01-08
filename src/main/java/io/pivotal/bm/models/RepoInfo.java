package io.pivotal.bm.models;

public class RepoInfo {
    private String status;
    private String buildState;
    private String buildColor;
    private int countCommitDifference;

    public RepoInfo() {
    }

    public RepoInfo(String status, String buildState, String buildColor, int countCommitDifference) {
        this.status = status;
        this.buildState = buildState;

        this.buildColor = buildColor;
        this.countCommitDifference = countCommitDifference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public String getBuildColor() {
        return buildColor;
    }

    public void setBuildColor(String buildColor) {
        this.buildColor = buildColor;
    }

    public int getCountCommitDifference() {
        return countCommitDifference;
    }

    public void setCountCommitDifference(int countCommitDifference) {
        this.countCommitDifference = countCommitDifference;
    }
}
