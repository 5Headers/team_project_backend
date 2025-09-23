package project_5headers.com.team_project.dto.account;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordReqDto {
    private String email;
    private String name;
}
