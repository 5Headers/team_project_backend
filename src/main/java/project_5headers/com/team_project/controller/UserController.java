package project_5headers.com.team_project.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.security.PrincipalDetails;


@Controller
public class UserController {



    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id, Model model) {
//        User user = userService.findById(id);
//        model.addAllAttributes("user", user);
        // userService 만든 다음에 주석 풀 예정입니다
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return "user/update";
    }
}
