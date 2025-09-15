package project_5headers.com.team_project.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.auth.SigninReqDto;
import project_5headers.com.team_project.dto.auth.SignupReqDto;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.AuthService;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;

    @GetMapping("/principal")
    public ResponseEntity<?>getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        ApiRespDto<?> apiRespDto = new ApiRespDto<>("success", "", principalUser);
        return ResponseEntity.ok(apiRespDto);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        ApiRespDto<?> response = authService.signup(signupReqDto);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        ApiRespDto<?> response = authService.signin(signinReqDto);
        return ResponseEntity.ok(response);
    }


}