package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.ArrayList;
import java.util.List;

public class LocationSearchStrategy implements TraineeshipSearchStrategy{
    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();


        String studentPreferredLocation = student.getPreferredLocation();
        if (studentPreferredLocation == null || studentPreferredLocation.isEmpty()) {
            return result;
        }

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            String positionLocation = pos.getLocation();

            if (studentPreferredLocation.equals(positionLocation)) {
                result.add(pos);
                break;
            }
        }


        return result;
    }
}
