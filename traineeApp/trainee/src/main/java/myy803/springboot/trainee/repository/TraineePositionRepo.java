package myy803.springboot.trainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.Optional;

public interface TraineePositionRepo extends JpaRepository<TraineePosition, Integer> {
    Optional<TraineePosition> findByPositionId(Integer traineeId);

    boolean existsByPositionId(Integer traineeId);

}
