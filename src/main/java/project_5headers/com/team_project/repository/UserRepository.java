package project_5headers.com.team_project.repository;

import project_5headers.com.team_project.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
}
