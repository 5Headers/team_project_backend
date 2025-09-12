//package project_5headers.com.team_project.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project_5headers.com.team_project.dto.ApiRespDto;
//import project_5headers.com.team_project.dto.AuthResponseDto;
//import project_5headers.com.team_project.dto.LoginRequestDto;
//import project_5headers.com.team_project.service.AuthService;
//
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping("/login")
//    public ApiRespDto<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
//        AuthResponseDto token = authService.login(request);
//        return new ApiRespDto<>("success", "로그인 성공", token);
//    }
//}
