package project_5headers.com.team_project.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.mapper.UserRoleMapper;
import project_5headers.com.team_project.entity.UserRole;

import java.util.Optional;

public class UserRoleRepository {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole){
        return userRoleMapper.addUserRole(userRole);
    }

    public Optional<UserRole> getUserRoleByUserIdAndRoleId(Integer userId, Integer roleId){
        return userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
    }

    public int updateRoleId(Integer userRoleId, Integer userId){
        return userRoleMapper.updateRoleId(userRoleId, userId);
    }
}
