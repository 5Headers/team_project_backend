package project_5headers.com.team_project.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import project_5headers.com.team_project.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class ChangePasswordReqDto {
    private Integer userId;
    private String oldPassword;
    private String newPassword;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(newPassword))
                .build();
    }
}
