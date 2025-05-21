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

        for (TraineePosition pos : positions) {
            if (pos == null) continue;

            List<String> positionTopics = pos.getSkillsList(); // skills treated as topics
            if (positionTopics == null || positionTopics.isEmpty()) continue;

            Set<String> positionTopicSet = new HashSet<>(positionTopics);

            // Υπολογισμός Jaccard similarity
            Set<String> intersection = new HashSet<>(studentInterestSet);
            intersection.retainAll(positionTopicSet);

            Set<String> union = new HashSet<>(studentInterestSet);
            union.addAll(positionTopicSet);

            double similarity = union.isEmpty() ? 0 : (double) intersection.size() / union.size();

            if (similarity > SIMILARITY_THRESHOLD) {
                result.add(pos);
            }
        }

        return result;
    }
}
