package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Company;
import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.TraineePosition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    public void saveProfile(Company company);
    public Company getCompanyProfile(String username);
    List<TraineePosition> getCompanyPositions(String username);
    void addPosition(String username, TraineePosition position);
    void deletePosition(String username, TraineePosition position);
    void saveOrUpdateEvaluation(Evaluation evaluation, String username);


    /*




    TraineePosition getTraineeshipById(Integer id);

    void updateTraineeship(TraineePosition position);

    void deleteTraineeship(Integer id);

    */

}
