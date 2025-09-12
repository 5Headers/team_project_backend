package project_5headers.com.team_project.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class MainController {

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

}
