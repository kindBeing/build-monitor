package io.pivotal.bm.models;

public class PRStats {
    private int totalPRs;
    private int mergedPRs;
    private int unmergedPRs;
    private int unknownPRs;

    public PRStats(int totalPRs, int unmergedPRs, int unknownPRs) {
        this.totalPRs = totalPRs;
        this.unmergedPRs = unmergedPRs;
        this.unknownPRs = unknownPRs;
        this.mergedPRs = totalPRs - ( unmergedPRs + unknownPRs);
    }

    public int getTotalPRs() {
        return totalPRs;
    }

    public int getMergedPRs() {
        return mergedPRs;
    }

    public int getUnmergedPRs() {
        return unmergedPRs;
    }

    public int getUnknownPRs() {
        return unknownPRs;
    }
}
