package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.ProfessorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProfessorInterestSearchStrategy implements ProfessorSearchStrategy {

    @Autowired
    private ProfessorRepo professorRepo;

    private static final double SIMILARITY_THRESHOLD = 0.3;

    @Override
    public List<Professor> searchSupervisor(TraineePosition position, List<TraineePosition> positions) {
        List<Professor> professors = professorRepo.findAll();
        List<Professor> result = new ArrayList<>();

        Set<String> requiredSkills = new HashSet<>(position.getSkillsList());

        for (Professor professor : professors) {
            if (professor.getInterests() == null) continue;

            Set<String> profInterests = new HashSet<>(professor.getInterestList());

            Set<String> intersection = new HashSet<>(requiredSkills);
            intersection.retainAll(profInterests);

            Set<String> union = new HashSet<>(requiredSkills);
            union.addAll(profInterests);

            double similarity = union.isEmpty() ? 0 : (double) intersection.size() / union.size();

            if (similarity > SIMILARITY_THRESHOLD) {
                result.add(professor);
            }
        }

        return result;
    }
}
