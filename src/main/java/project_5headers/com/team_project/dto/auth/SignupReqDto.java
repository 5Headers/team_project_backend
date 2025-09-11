package project_5headers.com.team_project.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import project_5headers.com.team_project.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class SignupReqDto {

    private String username;
    private String password;
    private String email;

    // User 엔티티로 변환
    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build();
    }
}
