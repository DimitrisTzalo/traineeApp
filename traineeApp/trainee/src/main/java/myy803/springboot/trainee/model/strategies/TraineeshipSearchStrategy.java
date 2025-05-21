package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;
import java.util.List;

public interface TraineeshipSearchStrategy {
    List<TraineePosition> search(Student student, List<TraineePosition> positions);

}
