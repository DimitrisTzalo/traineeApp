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

    private static final double THRESHOLD = 0.3;

    @Override
    public List<TraineePosition> search(TraineePosition position, List<TraineePosition> positions) {
        if (position.getSkills() == null || position.getSkills().isBlank()) {
            return Collections.emptyList();
        }

        Set<String> topics = Arrays.stream(position.getSkills().toLowerCase().split("[,;\\s]+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        return professorRepo.findAll().stream()
                .filter(professor -> {
                    if (professor.getInterests() == null || professor.getInterests().isBlank()) return false;

                    Set<String> interests = Arrays.stream(professor.getInterests().toLowerCase().split("[,;\\s]+"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toSet());

                    Set<String> intersection = new HashSet<>(topics);
                    intersection.retainAll(interests);

                    Set<String> union = new HashSet<>(topics);
                    union.addAll(interests);

                    double similarity = union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();

                    return similarity >= THRESHOLD;
                })
                .map(professor -> {
                    TraineePosition clone = new TraineePosition();
                    clone.setPositionId(position.getPositionId());
                    clone.setTitle(position.getTitle());
                    clone.setLocation(position.getLocation());
                    clone.setSkills(position.getSkills());
                    clone.setCompany(position.getCompany());
                    clone.setApplicant(position.getApplicant());
                    clone.setSupervisor(professor);
                    return clone;
                })
                .collect(Collectors.toList());
    }
}
