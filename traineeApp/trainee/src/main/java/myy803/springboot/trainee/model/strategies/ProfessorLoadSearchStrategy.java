package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.ProfessorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProfessorLoadSearchStrategy implements ProfessorSearchStrategy {

    @Autowired
    private ProfessorRepo professorRepo;

    @Override
    public List<TraineePosition> search(TraineePosition position, List<TraineePosition> positions) {
        Map<String, Long> loadMap = positions.stream()
                .filter(p -> p.getSupervisor() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getSupervisor().getUsername(),
                        Collectors.counting()
                ));

        return professorRepo.findAll().stream()
                .sorted((p1, p2) -> Long.compare(
                        loadMap.getOrDefault(p1.getUsername(), 0L),
                        loadMap.getOrDefault(p2.getUsername(), 0L)
                ))
                .map(professor -> {
                    TraineePosition temp = new TraineePosition();
                    temp.setPositionId(position.getPositionId());
                    temp.setTitle(position.getTitle());
                    temp.setLocation(position.getLocation());
                    temp.setSkills(position.getSkills());
                    temp.setCompany(position.getCompany());
                    temp.setApplicant(position.getApplicant());
                    temp.setSupervisor(professor);
                    //temp.setLoad(loadMap.getOrDefault(professor.getUsername(), 0L));
                    return temp;
                })
                .collect(Collectors.toList());
    }
}
