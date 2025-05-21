package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.*;

public class InterestSearchStrategy implements TraineeshipSearchStrategy {

    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        List<String> studentInterests = student.getInterestList();
        if (studentInterests == null || studentInterests.isEmpty()) {
            return result;
        }

        Map<TraineePosition, Integer> scoredPositions = new HashMap<>();

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            List<String> positionInterests = pos.getSkillsList(); // skills used as "interests"
            if (positionInterests == null || positionInterests.isEmpty()) continue;

            int score = 0;

            for (String interest : positionInterests) {
                if (interest == null) continue;

                // Προσθέτει περισσότερους βαθμούς για απόλυτη ταύτιση
                if (interest.equals(student.getInterests())) {
                    score += 3;
                } else if (studentInterests.contains(interest)) {
                    score++;
                }
            }

            if (score > 0) {
                scoredPositions.put(pos, score);
            }
        }

        // Ταξινόμηση με βάση το score (φθίνουσα σειρά)
        List<Map.Entry<TraineePosition, Integer>> sortedEntries = new ArrayList<>(scoredPositions.entrySet());
        sortedEntries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        // Τελικό αποτέλεσμα
        for (Map.Entry<TraineePosition, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }
}