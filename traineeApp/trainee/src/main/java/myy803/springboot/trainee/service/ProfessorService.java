package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProfessorService {

    public void saveProfile(Professor professor);
    public Professor getProfessorProfile(String username);
    public List<TraineePosition> getSupervisedPositions(String username);
    public void saveOrUpdateEvaluation(Evaluation evalForm, String username);
    // perimene na kaneis add kialla

}
