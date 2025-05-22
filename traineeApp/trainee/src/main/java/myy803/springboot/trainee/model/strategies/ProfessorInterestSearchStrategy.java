package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.Professor;
import java.util.*;

public class ProfessorInterestSearchStrategy implements ProfessorSearchStrategy {
    private static final double SIMILARITY_THRESHOLD = 0.3;

    @Override
    public List<TraineePosition> search(Professor professor, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        if (professor == null || professor.getInterests() == null || positions == null) {
            return result;
        }

        List<String> professorInterestsList = professor.getInterestList();
        if (professorInterestsList.isEmpty()) {
            return result;
        }
        Set<String> professorInterestSet = new HashSet<>(professorInterestsList);

        Map<TraineePosition, Integer> scoredPositions = new HashMap<>();

        for (TraineePosition pos : positions) {
            if (pos == null || pos.getSkills() == null) continue;

            List<String> positionSkillsList = pos.getSkillsList(); // υποθέτουμε πως ήδη έχει υλοποιηθεί
            Set<String> positionSkillSet = new HashSet<>(positionSkillsList);

            Set<String> intersection = new HashSet<>(professorInterestSet);
            intersection.retainAll(positionSkillSet);

            Set<String> union = new HashSet<>(professorInterestSet);
            union.addAll(positionSkillSet);

            double similarity = union.isEmpty() ? 0 : (double) intersection.size() / union.size();

            if (similarity > SIMILARITY_THRESHOLD) {
                int score = intersection.size(); // Βαθμολογία = πλήθος κοινών skills/interests
                scoredPositions.put(pos, score);
            }
        }

        // Ταξινόμηση βάσει score σε φθίνουσα σειρά
        List<Map.Entry<TraineePosition, Integer>> sorted = new ArrayList<>(scoredPositions.entrySet());
        sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for (Map.Entry<TraineePosition, Integer> entry : sorted) {
            result.add(entry.getKey());
        }

        return result;
    }
}
