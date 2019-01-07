package io.pivotal.bm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String displayWelComeScreen() {
        return "hello";
    }

    @PostMapping("/add")
    public String addPullRequest() {
        return "";
    }
}
