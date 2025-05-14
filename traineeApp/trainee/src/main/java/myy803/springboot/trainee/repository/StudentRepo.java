package myy803.springboot.trainee.repository;
import java.util.Optional;
import myy803.springboot.trainee.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, String> {

    Optional<Student> findByStudentName(String username);

    boolean existsByUsername(String username);

}
