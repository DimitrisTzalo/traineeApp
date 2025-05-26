package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.EvaluationType;
import myy803.springboot.trainee.repository.EvaluationRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepo evaluationRepo;

    @Override
    public void save(Evaluation evaluation) {
        evaluationRepo.save(evaluation);
    }

    @Override
    public Map<String, Double> calculateAverageEvaluationForPosition(Integer positionId) {
        List<Evaluation> evaluations = evaluationRepo.findByTraineePosition_PositionId(positionId);

        int countMotivation = 0, sumMotivation = 0;
        int countEffectiveness = 0, sumEffectiveness = 0;
        int countEfficiency = 0, sumEfficiency = 0;

        for (Evaluation e : evaluations) {
            if (e.getMotivation() > 0) {
                sumMotivation += e.getMotivation();
                countMotivation++;
            }
            if (e.getEffectiveness() > 0) {
                sumEffectiveness += e.getEffectiveness();
                countEffectiveness++;
            }
            if (e.getEfficiency() > 0) {
                sumEfficiency += e.getEfficiency();
                countEfficiency++;
            }
        }

        Map<String, Double> averages = new HashMap<>();
        averages.put("motivation", countMotivation > 0 ? (double) sumMotivation / countMotivation : 0.0);
        averages.put("effectiveness", countEffectiveness > 0 ? (double) sumEffectiveness / countEffectiveness : 0.0);
        averages.put("efficiency", countEfficiency > 0 ? (double) sumEfficiency / countEfficiency : 0.0);

        return averages;
    }


}
