package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.EvaluationType;
import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.EvaluationRepo;
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

    @Autowired
    private EvaluationRepo evaluationRepo;

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

    @Override
    public void saveOrUpdateEvaluation(Evaluation evalForm, String username) {
        Integer posId = evalForm.getTraineePosition() != null ? evalForm.getTraineePosition().getPositionId() : null;
        if (posId == null) return;

        TraineePosition position = traineePositionRepo.findById(posId).orElse(null);
        Professor professor = professorRepo.findByUsername(username).orElse(null);

        if (position == null || professor == null) return;

        Evaluation evaluation = evaluationRepo
                .findByTraineePosition_PositionIdAndProfessor_Username(posId, username)
                .orElse(new Evaluation());

        evaluation.setTraineePosition(position);
        evaluation.setProfessor(professor);
        evaluation.setEvaluationType(EvaluationType.PROFESSOR);

        evaluation.setMotivation(evalForm.getMotivation());
        evaluation.setEffectiveness(evalForm.getEffectiveness());
        evaluation.setEfficiency(evalForm.getEfficiency());
        evaluation.setFacilities(evalForm.getFacilities());
        evaluation.setGuidance(evalForm.getGuidance());

        evaluationRepo.save(evaluation);
    }



}
