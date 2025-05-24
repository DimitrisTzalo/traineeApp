package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
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
    private TraineePositionRepo traineePositionRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

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

    public List<TraineePosition> getAllTraineeships() {
        List<TraineePosition> allPositions = traineePositionRepo.findAll();

        List<TraineePosition> availablePositions = new ArrayList<TraineePosition>();
        for(TraineePosition position : allPositions) {

            if (!position.isAssigned())
                availablePositions.add(position);
        }

        return availablePositions;

    }
    @Override
    public void applyToTraineeship(String username, Integer traineeshipId) {

        Application application = new Application();

        Optional<Student> applicant = studentRepo.findByUsername(username);

        Optional<TraineePosition> position = traineePositionRepo.findByPositionId(traineeshipId);

        application.setApplicant(applicant.get());
        application.setPosition(position.get());
        applicant.get().getApplications().add(application);

        studentRepo.save(applicant.get());

    }

    @Override
    public boolean saveLogbook(String username, String logbookContent) {
        Optional<TraineePosition> assignedPosition = traineePositionRepo.findByApplicant_UsernameAndIsAssignedTrue(username);

        if (assignedPosition.isPresent()) {
            TraineePosition position = assignedPosition.get();
            position.setStudentLogBook(logbookContent);
            traineePositionRepo.save(position);
            return true;
        }

        return false;
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

