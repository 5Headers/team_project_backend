package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoleMapper {

    int addRole(Role role);
    Optional<Role> getRoleById(Integer roleId);
    Optional<Role> getRoleByName(String roleName);
    List<Role> getRoleList();
}
