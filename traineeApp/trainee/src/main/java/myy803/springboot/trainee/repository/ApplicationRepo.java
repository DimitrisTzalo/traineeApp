package myy803.springboot.trainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import myy803.springboot.trainee.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {

    Optional<Application> findByApplicant_UsernameAndPosition_PositionId(String username, Integer positionId);

    List<Application> findByPosition_PositionId(Integer positionId);

    List<Application> findByApplicant_Username(String username);

    boolean existsByApplicant_UsernameAndPosition_PositionId(String username, Integer positionId);

    void deleteByApplicant_UsernameAndPosition_PositionIdNot(String username, Integer positionId);
    void deleteByPosition_PositionIdAndApplicant_UsernameNot(Integer positionId, String username);

}
