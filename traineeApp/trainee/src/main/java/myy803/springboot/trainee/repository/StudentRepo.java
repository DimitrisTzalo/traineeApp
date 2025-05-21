package myy803.springboot.trainee.repository;
import java.util.Optional;
import myy803.springboot.trainee.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, String> {

    Optional<Student> findByUsername(String username);

    boolean existsByUsername(String username);
    Optional<Student> findById(Integer id);

}
