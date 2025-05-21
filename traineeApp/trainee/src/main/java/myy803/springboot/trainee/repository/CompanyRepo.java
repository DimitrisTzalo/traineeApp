package myy803.springboot.trainee.repository;

import java.util.Optional;
import myy803.springboot.trainee.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepo  extends JpaRepository<Company, Integer> {

    Optional<Company> findByUsername(String username);

    boolean existsByUsername(String username);
}
