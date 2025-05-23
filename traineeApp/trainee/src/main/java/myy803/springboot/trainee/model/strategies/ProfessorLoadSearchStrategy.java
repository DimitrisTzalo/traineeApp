package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.ProfessorRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProfessorLoadSearchStrategy implements ProfessorSearchStrategy {

    @Autowired
    private ProfessorRepo professorRepo;
    @Autowired
    private TraineePositionRepo traineePositionRepo;


    @Override
    public List<Professor> searchSupervisor(TraineePosition position, List<TraineePosition> positions) {
        List<Professor> professors = professorRepo.findAll();

        return professors.stream()
                .sorted(Comparator.comparingInt(prof -> (int) traineePositionRepo.countBySupervisor_UsernameAndIsAssignedTrue(prof.getUsername())))
                .collect(Collectors.toList());
    }
}
