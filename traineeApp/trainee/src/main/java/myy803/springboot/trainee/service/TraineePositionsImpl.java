package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePositions;
import myy803.springboot.trainee.repository.TraineePositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TraineePositionsImpl implements TraineePositionsService{

    @Autowired
    private TraineePositionsRepo traineePositionsRepo;

    @Override
    public void save(TraineePositions traineePositions) {

        traineePositionsRepo.save(traineePositions);
    }
}
