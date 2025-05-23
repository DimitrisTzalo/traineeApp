package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.*;

public class InterestSearchStrategy implements TraineeshipSearchStrategy {

    private static final double SIMILARITY_THRESHOLD = 0.3;

    @Override
    public List<TraineePosition> search(Student student, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        List<String> studentInterests = student.getInterestList();
        if (studentInterests == null || studentInterests.isEmpty()) {
            return result;
        }

        Set<String> studentInterestSet = new HashSet<>(studentInterests);
        Map<TraineePosition, Integer> scoredPositions = new HashMap<>();

        for (TraineePosition pos : positions) {
            if (pos == null || pos.isAssigned()) continue;

            List<String> positionTopics = pos.getSkillsList();
            if (positionTopics == null || positionTopics.isEmpty()) continue;

            Set<String> positionTopicSet = new HashSet<>(positionTopics);

            // Υπολογισμός Jaccard similarity
            Set<String> intersection = new HashSet<>(studentInterestSet);
            intersection.retainAll(positionTopicSet);

            Set<String> union = new HashSet<>(studentInterestSet);
            union.addAll(positionTopicSet);

            double similarity = union.isEmpty() ? 0 : (double) intersection.size() / union.size();

            if (similarity > SIMILARITY_THRESHOLD) {
                int score = intersection.size(); // Βαθμολογία: πλήθος κοινών ενδιαφερόντων
                scoredPositions.put(pos, score);
            }
        }

        List<Map.Entry<TraineePosition, Integer>> sortedEntries = new ArrayList<>(scoredPositions.entrySet());
        sortedEntries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        for (Map.Entry<TraineePosition, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }
}
