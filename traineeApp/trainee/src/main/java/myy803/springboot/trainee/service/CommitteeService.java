package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.model.Committee;
import myy803.springboot.trainee.model.TraineePosition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommitteeService {

    public void saveProfile(Committee committee);
    public Committee getCommitteeProfile(String username);
    public List<Application> getApplicationsForAvailablePositions();
    public List<TraineePosition> getAvailableTraineeships();
    public List<TraineePosition> searchForStudent(String username, String criteria);

}
