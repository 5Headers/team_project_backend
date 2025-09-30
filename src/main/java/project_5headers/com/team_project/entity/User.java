package project_5headers.com.team_project.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String name;
    private String email;
    private String profileImg;
    private String password;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
    private List<UserRole> userRoles;
}
