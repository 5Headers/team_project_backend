//package project_5headers.com.team_project.controller;
//
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//
//@Controller
//public class UserController {
//
//
//
//    @GetMapping("user/{id}")
//    public String profile(@PathVariable int id) {
//        return "user/profile";
//    }
//
//    @GetMapping("/user/{id}/update")
//    public String update(@PathVariable int id, @AuthenticationPrincipal) {
//        return "user/update";
//    }
//}
