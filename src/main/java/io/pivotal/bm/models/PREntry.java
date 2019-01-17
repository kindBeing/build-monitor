package io.pivotal.bm.models;

public class PREntry {
    private String prId;

    private String status;

    public PREntry(String prid, String status) {
        this.prId = prid;
        this.status = status;
    }

    public String getPrId() {
        return prId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
