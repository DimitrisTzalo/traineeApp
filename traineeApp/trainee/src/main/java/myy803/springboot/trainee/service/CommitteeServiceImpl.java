package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Committee;
import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.strategies.InterestAndLocationSearchStrategy;
import myy803.springboot.trainee.model.strategies.InterestSearchStrategy;
import myy803.springboot.trainee.model.strategies.LocationSearchStrategy;
import myy803.springboot.trainee.model.strategies.TraineeshipSearchStrategy;
import myy803.springboot.trainee.repository.CommitteeRepo;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
