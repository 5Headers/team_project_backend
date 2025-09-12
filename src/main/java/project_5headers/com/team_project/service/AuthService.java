package project_5headers.com.team_project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.dto.AuthResponseDto;
import project_5headers.com.team_project.dto.LoginRequestDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // private final JwtTokenProvider jwtTokenProvider; // JWT 미사용 중이므로 주석처리

    public AuthResponseDto login(LoginRequestDto request) {
        // 사용자 조회
        User user = userRepository.getUserByUsername(request.getUsername())
                // userRepository 수정 중으로 findByUsername도 수정할 예정입니다 (9/11)


                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 미사용 → 토큰 없이 임시 success 메시지를 accessToken 자리에 사용
        return new AuthResponseDto("login-success-" + user.getUsername());
    }


}
