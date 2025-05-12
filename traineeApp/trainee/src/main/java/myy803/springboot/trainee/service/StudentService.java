package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Student;
import org.springframework.stereotype.Service;


@Service
public interface StudentService {

    public void saveProfile(Student student);


}
