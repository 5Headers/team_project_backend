package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.auth.SigninReqDto;
import project_5headers.com.team_project.dto.auth.SignupReqDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // ✅ 현재 로그인한 사용자 정보 (DB 최신값 포함)
    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal PrincipalUser principalUser) {
        Optional<User> userOpt = userRepository.getUserByUserId(principalUser.getUserId());

        if (userOpt.isEmpty()) {
            return ResponseEntity.ok(new ApiRespDto<>("failed", "사용자를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new ApiRespDto<>("success", "사용자 조회 성공", userOpt.get()));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        ApiRespDto<?> response = authService.signup(signupReqDto);
        return ResponseEntity.ok(response);
    }

    // 로그인 (JWT 토큰 발급 포함)
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        ApiRespDto<?> response = authService.signin(signinReqDto);
        return ResponseEntity.ok(response);
    }

    // 아이디 중복 확인
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(authService.checkUsername(username));
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(authService.checkEmail(email));
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdrawUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        ApiRespDto<?> response = authService.withdrawUser(principalUser.getUserId());
        return ResponseEntity.ok(response);
    }
}
