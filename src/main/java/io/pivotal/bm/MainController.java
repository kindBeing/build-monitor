package io.pivotal.bm;

import io.pivotal.bm.models.PREntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private BMRepository bmRepository;


    @GetMapping("/")
    public String displayWelComeScreen(Model model){
        GitInfoProvider gitInfoProvider = new GitInfoProvider(bmRepository);
        model.addAttribute("git_info", gitInfoProvider.getInfo());
        return "customer/mainscreen.html";
    }

    // String, Request Body ?
    @PostMapping("/admin/{prid}")
    public ResponseEntity<String> create(@PathVariable String prid) {
        PREntry prEntry = new PREntry(prid, "UNMERGED");
        bmRepository.create(prEntry);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
