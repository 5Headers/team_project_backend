package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    int addUser(User user);
    Optional<User> getUserById(Integer userId);
    Optional<User> getUserByEmail(String email);
    List<User> getUserList();
    int updateUser(User user);
    int removeUserById(Integer userId);
}
