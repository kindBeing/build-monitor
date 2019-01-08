package io.pivotal.bm;

import org.springframework.beans.factory.annotation.Value;

public class URLProvider {
    @Value("${ORG}") String ORG;
    @Value("${REPO}") String REPO;
    @Value("${BRANCH}") String BRANCH;
    @Value("${UPSTREAM_ORG}") String UPSTREAM_ORG;
    @Value("${UPSTREAM_REPO}") String UPSTREAM_REPO;
    @Value("${UPSTREAM_BRANCH}") String UPSTREAM_BRANCH;
    @Value("${ACCESS_TOKEN}") String ACCESS_TOKEN;

    public String getCompareURL(){
        return "https://api.github.com/repos/"
                + ORG +"/" + REPO + "/compare/" + BRANCH +
                "..." + UPSTREAM_REPO + ":" + UPSTREAM_BRANCH +
                "?ACCESS_TOKEN=" + ACCESS_TOKEN;
    }
    
    public String getBranchURL() {
        return "https://api.github.com/repos/"
                + UPSTREAM_ORG + "/" + UPSTREAM_REPO + "/branches/" + UPSTREAM_BRANCH +
                "?ACCESS_TOKEN=" + ACCESS_TOKEN;
    }

    public String getBuildURL(String branch_sha){
        return "https://api.github.com/repos/"
                + UPSTREAM_ORG + "/" + UPSTREAM_REPO + "/commits/" + branch_sha +
                "/status?ACCESS_TOKEN=" + ACCESS_TOKEN;
    }

    public String getPullURL(String prId) {
        return "https://api.github.com/repos/"
                + UPSTREAM_ORG + "/" + UPSTREAM_REPO + "/pulls/" + prId +
                "/merge?access_token=" + ACCESS_TOKEN;
    }
}
