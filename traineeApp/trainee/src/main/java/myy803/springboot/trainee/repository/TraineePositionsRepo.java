package myy803.springboot.trainee.repository;

import myy803.springboot.trainee.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import myy803.springboot.trainee.model.TraineePositions;

import java.util.Optional;

public interface TraineePositionsRepo extends JpaRepository<TraineePositions, Integer> {
    Optional<TraineePositions> findByPositionId(Integer traineeId);

    boolean existsByPositionId(Integer traineeId);

}
