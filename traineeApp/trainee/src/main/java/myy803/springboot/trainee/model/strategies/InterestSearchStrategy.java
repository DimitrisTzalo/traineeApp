package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterestSearchStrategy implements TraineeshipSearchStrategy {
    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();


        List<String> studentInterests = student.getInterestList();
        if (studentInterests == null || studentInterests.isEmpty()) {
            return result;
        }

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            List<String> positionInterests = pos.getSkillsList();
            if (positionInterests == null || positionInterests.isEmpty()) continue;

            for (String interest : positionInterests) {
                if(interest != null && interest.equals(student.getInterests())) {
                    result.add(pos);
                    continue;
                }
                if (interest != null && studentInterests.contains(interest) && !result.contains(pos)) {
                    result.add(pos);
                    continue;
                }
            }
        }

        return result;
    }
}
