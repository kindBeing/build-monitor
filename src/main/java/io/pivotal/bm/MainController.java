package io.pivotal.bm;

import io.pivotal.bm.domain.PRDBRepository;
import io.pivotal.bm.domain.RepoDBRepository;
import io.pivotal.bm.models.DisplayObject;
import io.pivotal.bm.models.PREntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private RepoDBRepository repoRepository;
    @Autowired
    private PRDBRepository prRepository;

    @GetMapping("/")
    public String displayWelComeScreen(Model model){
        GitInfoProvider gitInfoProvider = new GitInfoProvider(repoRepository, prRepository);
        DisplayObject displayObject = gitInfoProvider.getInfo(); // Temp

        model.addAttribute("pull_requests", displayObject.getPrEntries());
        model.addAttribute("pr_stats", displayObject.getPrStats());
        model.addAttribute("git_repo", displayObject.getRepoInfo());
        return "mainscreen.html";
    }

    @PostMapping("/admin/{prid}")
    public ResponseEntity<String> create(@PathVariable String prid) {
        PREntry prEntry = new PREntry(prid, "UNMERGED");
        prRepository.create(prEntry);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
