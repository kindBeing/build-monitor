package io.pivotal.bm;

import io.pivotal.bm.domain.PRDBRepository;
import io.pivotal.bm.domain.RepoDBRepository;
import io.pivotal.bm.models.DisplayObject;
import io.pivotal.bm.models.PREntry;
import io.pivotal.bm.models.PRStats;
import io.pivotal.bm.models.RepoInfo;

import java.util.ArrayList;
import java.util.List;

public class GitInfoProvider {
    private RepoDBRepository repoRepository;
    private PRDBRepository prRepository;
    private List<PREntry> prEntryList;
    private PRStats prStats;
    private RepoInfo repoInfo;


    public GitInfoProvider(RepoDBRepository repoRepository, PRDBRepository prRepository) {
        this.repoRepository = repoRepository;
        this.prRepository = prRepository;
        this.prEntryList = new ArrayList<>();
    }

    public DisplayObject getInfo(){
        prEntryList = prRepository.list();
        prStats = new PRStats(prEntryList.size(), prRepository.countUnmerged(), prRepository.countUnknown());
        repoInfo = repoRepository.getRepoInfo();
        DisplayObject displayObject = new DisplayObject(prEntryList, prStats, repoInfo);

        return displayObject;
    }
}
