package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.ArrayList;
import java.util.List;


public class InterestAndLocationSearchStrategy implements TraineeshipSearchStrategy {

    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        List<String> studentInterests = student.getInterestList();
        String studentPreferredLocation = student.getPreferredLocation();
        if (studentPreferredLocation == null || studentPreferredLocation.isEmpty() ||
        (studentInterests == null || studentInterests.isEmpty())) {
            return result;
        }

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            List<String> positionInterests = pos.getSkillsList();
            if (positionInterests == null || positionInterests.isEmpty()) continue;
            String positionLocation = pos.getLocation();

            for (String interest : positionInterests) {
                if (interest != null && studentInterests.contains(interest) && (studentPreferredLocation.equals(positionLocation))) {
                    result.add(pos);
                    break;
                }
            }


        }
        return result;
    }
}
