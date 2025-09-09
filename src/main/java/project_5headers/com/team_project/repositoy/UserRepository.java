package project_5headers.com.team_project.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import project_5headers.com.team_project.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //username으로 사용자 조회하기
    Optional<User> findByUsername(String username);

    //email으로 사용자 조회하기 (회원가입 시에 중복 체크용도)
    Optional<User> findByEmail(String email);
}
