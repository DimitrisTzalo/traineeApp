package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineePositionImpl implements TraineePositionService {

    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @Override
    public void save(TraineePosition traineePosition) {

        traineePositionRepo.save(traineePosition);
    }
}
