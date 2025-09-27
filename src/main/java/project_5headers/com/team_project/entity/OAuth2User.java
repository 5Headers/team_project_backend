package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class OAuth2User {
    private Integer oAuth2UserId;
    private Integer userId;
    private String provider;
    private String providerUserId;
    private LocalDateTime crateDt;
    private LocalDateTime updateDt;
}