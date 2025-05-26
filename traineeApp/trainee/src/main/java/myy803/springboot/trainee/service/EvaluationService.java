package myy803.springboot.trainee.service;




import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.EvaluationType;
import myy803.springboot.trainee.model.TraineePosition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EvaluationService {
    void save(Evaluation evaluation);
    public Map<String, Double> calculateAverageEvaluationForPosition(Integer positionId);
}
