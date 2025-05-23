package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.ProfessorRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfessorRepo professorRepo;

    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @Override
    public void saveProfile(Professor professor) {
        Optional<Professor> existing = professorRepo.findByUsername(professor.getUsername());

        if (existing.isPresent()) {
            Professor existingProfessor = existing.get();
            existingProfessor.setProfessorName(professor.getProfessorName());
            existingProfessor.setInterests(professor.getInterests());
            existingProfessor.setSupervisedPositions(professor.getSupervisedPositions());

            professorRepo.save(existingProfessor);
        } else {
            professorRepo.save(professor);
        }
    }

    @Override
    public Professor getProfessorProfile(String username) {
        return professorRepo.findByUsername(username).orElse(new Professor(username));
    }

    @Override
    public List<TraineePosition> getSupervisedPositions(String username) {
        return traineePositionRepo.findBySupervisor_UsernameAndIsAssignedTrue(username);
    }

}
