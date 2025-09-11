package project_5headers.com.team_project.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninReqDto {

    private String username;
    private String password;

    // 로그인용 DTO라서 toEntity는 필요 없고,
    // Service에서 바로 username/password 검증에 사용 가능
}
