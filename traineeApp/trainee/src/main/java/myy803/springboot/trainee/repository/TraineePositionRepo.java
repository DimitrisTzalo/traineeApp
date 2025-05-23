package myy803.springboot.trainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.List;
import java.util.Optional;

public interface TraineePositionRepo extends JpaRepository<TraineePosition, Integer> {
    Optional<TraineePosition> findByPositionId(Integer traineeId);

    Optional<TraineePosition> findByApplicant_UsernameAndIsAssignedTrue(String username);
    List<TraineePosition> findByIsAssignedTrueAndSupervisorIsNull();

    Integer countBySupervisor_UsernameAndIsAssignedTrue(String username);

    boolean existsBySupervisor_UsernameAndPositionId(String username, Integer positionId);
    boolean existsByPositionId(Integer traineeId);

}
