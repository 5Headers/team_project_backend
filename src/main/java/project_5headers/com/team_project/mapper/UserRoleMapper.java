package project_5headers.com.team_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project_5headers.com.team_project.entity.UserRole;

import java.util.Optional;

@Mapper
public interface UserRoleMapper {
    int addUserRole(UserRole userRole);
    Optional<UserRole> getUserRoleByUserIdAndRoleId(Integer userId, Integer roleId);
    int updateRoleId(Integer userRoleId, Integer userId);
}
