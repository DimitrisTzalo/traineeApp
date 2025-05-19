package myy803.springboot.trainee.repository;

import java.util.Optional;
import myy803.springboot.trainee.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepo extends JpaRepository<Professor, String> {

    Optional<Professor> findByUsername(String username);

    boolean existsByUsername(String username);

}
