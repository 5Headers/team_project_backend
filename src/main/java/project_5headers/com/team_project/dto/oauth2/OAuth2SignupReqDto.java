package project_5headers.com.team_project.dto.oauth2;

import lombok.Data;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project_5headers.com.team_project.entity.OAuth2User;
import project_5headers.com.team_project.entity.User;

@Data
public class OAuth2SignupReqDto {
    private String username;
    private String name;
    private String password;
    private String email;
    private String provider;
    private String providerUserId;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .name(name)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build();
    }

    public OAuth2User toOAuth2User(Integer userId) {
        return OAuth2User.builder()
                .userId(userId)
                .provider(provider)
                .providerUserId(providerUserId)
                .build();
    }
}
