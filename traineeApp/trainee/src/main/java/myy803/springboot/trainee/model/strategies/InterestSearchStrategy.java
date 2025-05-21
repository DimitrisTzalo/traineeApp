package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.ArrayList;
import java.util.List;

public class InterestSearchStrategy implements TraineeshipSearchStrategy {
    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        List<String> studentInterests = student.getInterestList();


        for (TraineePosition pos : positions) {
            boolean interestMatch = studentInterests.contains(pos.getSkills());

            if (interestMatch) {
                result.add(pos);
            }
        }

        return result;
    }
}
