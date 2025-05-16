package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePositions;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StudentService {

    public void saveProfile(Student student);
    public Student getStudentProfile(String username);

    void applyToTraineeship(String username, Integer studentId);


    List<TraineePositions> getAllTraineeships();
}
