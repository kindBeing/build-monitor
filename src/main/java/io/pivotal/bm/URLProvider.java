package io.pivotal.bm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLProvider {

    private String org;
    private String repo;
    private String branch;
    private String upstreamOrg;
    private String upstreamRepo;
    private String upstreamBranch;
    private String accessToken;

    @Autowired
    public URLProvider(@Value("${ORG}") String ORG,
                       @Value("${REPO}") String REPO,
                       @Value("${BRANCH}") String BRANCH,
                       @Value("${UPSTREAM_ORG}") String UPSTREAM_ORG,
                       @Value("${UPSTREAM_REPO}") String UPSTREAM_REPO,
                       @Value("${UPSTREAM_BRANCH}") String UPSTREAM_BRANCH,
                       @Value("${ACCESS_TOKEN}") String ACCESS_TOKEN) {
        this.org = ORG;
        this.repo = REPO;
        this.branch = BRANCH;
        this.upstreamOrg = UPSTREAM_ORG;
        this.upstreamBranch = UPSTREAM_BRANCH;
        this.upstreamRepo = UPSTREAM_REPO;
        this.accessToken = ACCESS_TOKEN;
    }

    public String getCompareURL(){
        return "https://api.github.com/repos/"
                + org +"/" + repo + "/compare/" + branch +
                "..." + upstreamRepo + ":" + upstreamBranch +
                "?accessToken=" + accessToken;
    }
    
    public String getBranchURL() {
        return "https://api.github.com/repos/"
                + upstreamOrg + "/" + upstreamRepo + "/branches/" + upstreamBranch +
                "?accessToken=" + accessToken;
    }

    public String getBuildURL(String branch_sha){
        return "https://api.github.com/repos/"
                + upstreamOrg + "/" + upstreamRepo + "/commits/" + branch_sha +
                "/status?accessToken=" + accessToken;
    }

    public String getPullURL(String prId) {
        return "https://api.github.com/repos/"
                + upstreamOrg + "/" + upstreamRepo + "/pulls/" + prId +
                "/merge?access_token=" + accessToken;
    }
}
