package myy803.springboot.trainee.repository;

import myy803.springboot.trainee.model.Committee;
import myy803.springboot.trainee.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommitteeRepo extends JpaRepository<Committee, String> {

    Optional<Committee> findByUsername(String username);

    boolean existsByUsername(String username);
}
