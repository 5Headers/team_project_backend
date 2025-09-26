package project_5headers.com.team_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project_5headers.com.team_project.entity.Part;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Integer> {
    Optional<Part> findByNameContaining(String keyword);
}
