package myy803.springboot.trainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.EvaluationType;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepo extends JpaRepository<Evaluation, Integer> {

    Optional<Evaluation> findByEvaluationId(Integer integer);

    Optional<Evaluation> findByTraineePosition_PositionIdAndCompany_Username(Integer posId, String username);
    Optional<Evaluation> findByTraineePosition_PositionIdAndProfessor_Username(Integer posId, String username);

    List<Evaluation> findByCompany_Username(String username);
}
