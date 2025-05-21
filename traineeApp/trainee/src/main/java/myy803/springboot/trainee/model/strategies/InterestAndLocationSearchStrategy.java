package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.*;

public class InterestAndLocationSearchStrategy implements TraineeshipSearchStrategy {

    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        List<String> studentInterests = student.getInterestList();
        String studentPreferredLocation = student.getPreferredLocation();

        if (studentPreferredLocation == null || studentPreferredLocation.isEmpty() ||
                studentInterests == null || studentInterests.isEmpty()) {
            return result;
        }

        Map<TraineePosition, Integer> scoredPositions = new HashMap<>();

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            String positionLocation = pos.getLocation();
            if (!studentPreferredLocation.equals(positionLocation)) continue; // Μόνο αν ταιριάζει η τοποθεσία

            List<String> positionSkills = pos.getSkillsList();
            if (positionSkills == null || positionSkills.isEmpty()) continue;

            int score = 0;

            for (String skill : positionSkills) {
                if (skill != null && studentInterests.contains(skill)) {
                    score++;
                }
            }

            // Extra βαθμοί για απόλυτη ταύτιση
            if (positionSkills.contains(student.getInterests())) {
                score += 3;
            }

            if (score > 0) {
                scoredPositions.put(pos, score);
            }
        }

        // Ταξινόμηση κατά φθίνουσα σειρά score
        List<Map.Entry<TraineePosition, Integer>> sortedEntries = new ArrayList<>(scoredPositions.entrySet());
        sortedEntries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        for (Map.Entry<TraineePosition, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }
}
