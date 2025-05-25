package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.EvaluationType;
import myy803.springboot.trainee.repository.EvaluationRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepo evaluationRepo;

    @Override
    public void save(Evaluation evaluation) {
        evaluationRepo.save(evaluation);
    }

}
