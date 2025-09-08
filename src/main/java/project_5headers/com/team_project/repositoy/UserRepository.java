package project_5headers.com.team_project.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import project_5headers.com.team_project.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
