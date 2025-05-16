package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.TraineePositions;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.TraineePositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TraineePositionsRepo traineePositionsRepo;

    @Override
    public void saveProfile(Student student) {
        Optional<Student> existing = studentRepo.findByUsername(student.getUsername());


        if (existing.isPresent()) {
            Student existingStudent = existing.get();
            existingStudent.setStudentName(student.getStudentName());
            existingStudent.setStudentAM(student.getStudentAM());
            existingStudent.setAverageGrade(student.getAverageGrade());
            existingStudent.setPreferredLocation(student.getPreferredLocation());
            existingStudent.setInterests(student.getInterests());
            existingStudent.setSkills(student.getSkills());
            existingStudent.setLookingForTraineeship(student.isLookingForTraineeship());

            studentRepo.save(existingStudent);
        } else {
            studentRepo.save(student);
        }
    }

    @Override
    public Student getStudentProfile(String username) {
        return studentRepo.findByUsername(username).orElse(new Student(username));

    }

    @Override
    public List<TraineePositions> getAllTraineeships() {
        List<TraineePositions> allPositions = traineePositionsRepo.findAll();

        List<TraineePositions> availablePositions = new ArrayList<TraineePositions>();
        for(TraineePositions position : allPositions) {

            if(position.getApplicantName() == null)
                availablePositions.add(position);
        }

        return availablePositions;

    }

    public void applyToTraineeship(String username, Integer traineeshipId) {

        TraineePositions positions = new TraineePositions();

        Optional<Student> optionalStudent = studentRepo.findByUsername(username);
        Optional<TraineePositions> optionalTraineePositions = traineePositionsRepo.findByPositionId(traineeshipId);

        positions.setApplicantName(optionalStudent.get());
        positions.setDescription(optionalTraineePositions.get().getDescription());

        optionalStudent.get().addTraineeship(positions);

        studentRepo.save(optionalStudent.get());

    }




    /*
    @Override
    public boolean isUserPresent(User user) {
        Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
        return storedUser.isPresent();
    }

    // Method defined in Spring Security UserDetailsService interface
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // orElseThrow method of Optional container that throws an exception if Optional result  is null
        return userDAO.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND %s", username)
                ));
    }

     */
}

