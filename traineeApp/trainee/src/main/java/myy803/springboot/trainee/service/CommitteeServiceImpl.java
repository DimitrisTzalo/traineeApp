package myy803.springboot.trainee.service;

import jakarta.transaction.Transactional;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.model.strategies.*;
import myy803.springboot.trainee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommitteeServiceImpl implements CommitteeService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CommitteeRepo committeeRepo;

    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ProfessorRepo professorRepo;

    @Autowired
    private ProfessorInterestSearchStrategy interestStrategy;

    @Autowired
    private ProfessorLoadSearchStrategy loadStrategy;


    @Override
    public void saveProfile(Committee committee) {
        Optional<Committee> existing = committeeRepo.findByUsername(committee.getUsername());

        if (existing.isPresent()) {
            Committee existingCommittee = existing.get();
            existingCommittee.setCommitteeName(committee.getCommitteeName());


            committeeRepo.save(existingCommittee);
        } else {
            committeeRepo.save(committee);
        }
    }

    @Override
    public Committee getCommitteeProfile(String username) {
        return committeeRepo.findByUsername(username).orElse(new Committee(username));
    }

    @Override
    public List<Application> getApplicationsForAvailablePositions() {
        List<TraineePosition> availablePositions = getAvailableTraineeships();
        List<Application> applications = new ArrayList<>();

        for (TraineePosition position : availablePositions) {
            if (!position.isAssigned()) {

                List<Application> apps = applicationRepo.findByPosition_PositionId(position.getPositionId());
                applications.addAll(apps);
            }
        }

        return applications;
    }

    public List<TraineePosition> getAvailableTraineeships() {
        List<TraineePosition> allPositions = traineePositionRepo.findAll();
        List<TraineePosition> availablePositions = new ArrayList<TraineePosition>();
        for(TraineePosition position : allPositions) {

            if (!position.isAssigned())
                availablePositions.add(position);
        }
        return availablePositions;
    }

    @Override
    public List<TraineePosition> searchForStudent(String username, String criteria) {
        Student student = studentRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found with username: " + username));

        List<TraineePosition> allPositions = traineePositionRepo.findAll();

        TraineeshipSearchStrategy strategy;

        switch (criteria) {
            case "interest":
                strategy = new InterestSearchStrategy();
                break;
            case "location":
                strategy = new LocationSearchStrategy();
                break;
            case "both":
                strategy = new InterestAndLocationSearchStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid search criteria");
        }

        return strategy.search(student, allPositions);
    }

    @Transactional
    @Override
    public void assignPositiontoStudent(String committeeUsername, Integer positionId, String studentUsername){

        Optional<Student> applicant = studentRepo.findByUsername(studentUsername);
        Optional<TraineePosition> position = traineePositionRepo.findById(positionId);
        Optional<Committee> selectedCommittee = committeeRepo.findByUsername(committeeUsername);


        Student student = applicant.get();
        TraineePosition traineePosition = position.get();
        Committee committee = selectedCommittee.get();

        traineePosition.setApplicant(student);
        traineePosition.setCommittee(committee);
        traineePosition.setIsAssigned(true);

        traineePositionRepo.save(traineePosition);

        applicationRepo.deleteByApplicant_UsernameAndPosition_PositionIdNot(studentUsername, positionId);
        applicationRepo.deleteByPosition_PositionIdAndApplicant_UsernameNot(positionId, studentUsername);

    }

    @Override
    public List<Professor> searchProfessor(Integer positionId, String criteria) {
        Optional<TraineePosition> optional = traineePositionRepo.findById(positionId);
        if (!optional.isPresent()) return Collections.emptyList();

        TraineePosition position = optional.get();

        // Φέρνουμε όλες τις assigned θέσεις (χρήσιμες για το load)
        List<TraineePosition> assignedPositions = traineePositionRepo.findByIsAssignedTrueAndSupervisorIsNull();

        List<Professor> results;

        ProfessorSearchStrategy strategy;

        switch (criteria) {
            case "interest":
                results = interestStrategy.searchSupervisor(position, assignedPositions);
                break;

            case "load":
                results = loadStrategy.searchSupervisor(position, assignedPositions);
                for (Professor p : results) {
                    int currentLoad = traineePositionRepo.countBySupervisor_UsernameAndIsAssignedTrue(p.getUsername()); // ή όπως αλλιώς μετράς το φορτίο
                    p.setLoad(currentLoad);
                }
                
                break;
            default:
                throw new IllegalArgumentException("Invalid search criteria");
        }

        return results;
    }


    @Transactional
    @Override
    public void assignPositiontoProfessor(String committeeUsername, Integer positionId, String professorUsername){
        Optional<Professor> supervisor = professorRepo.findByUsername(professorUsername);
        Optional<TraineePosition> position = traineePositionRepo.findById(positionId);
        Optional<Committee> selectedCommittee = committeeRepo.findByUsername(committeeUsername);

        Professor professor = supervisor.get();
        TraineePosition traineePosition = position.get();
        Committee committee = selectedCommittee.get();

        traineePosition.setSupervisor(professor);
        traineePosition.setCommittee(committee);



        traineePositionRepo.save(traineePosition);
    }

}
