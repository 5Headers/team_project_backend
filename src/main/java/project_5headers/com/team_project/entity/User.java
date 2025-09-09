package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}
