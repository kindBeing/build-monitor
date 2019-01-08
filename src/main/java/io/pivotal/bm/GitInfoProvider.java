package io.pivotal.bm;

import io.pivotal.bm.models.DisplayObject;
import io.pivotal.bm.models.PREntry;
import io.pivotal.bm.models.PRStats;
import io.pivotal.bm.models.RepoInfo;

import java.util.ArrayList;
import java.util.List;

public class GitInfoProvider {
    private BMRepository bmRepository;
    private List<PREntry> prEntryList;
    private PRStats prStats;
    private RepoInfo repoInfo;


    public GitInfoProvider(BMRepository bmRepository) {
        this.bmRepository = bmRepository;
        this.prEntryList = new ArrayList<>();
    }

    public DisplayObject getInfo(){
        prEntryList = bmRepository.list();
        prStats = new PRStats(prEntryList.size(), bmRepository.countUnmerged(), bmRepository.countUnknown());
        repoInfo = bmRepository.getRepoInfo();


        DisplayObject displayObject = new DisplayObject(prEntryList, prStats, repoInfo);
        return displayObject;
    }
}
