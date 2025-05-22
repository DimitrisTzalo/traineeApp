package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;
import java.util.List;

public interface ProfessorSearchStrategy {
    List<TraineePosition> search(Professor professor, List<TraineePosition> positions);

}

