package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleNameKor;
}
