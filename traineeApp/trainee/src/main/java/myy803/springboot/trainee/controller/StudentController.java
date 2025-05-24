package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.service.StudentService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    StudentService studentService;
    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @RequestMapping("/student/dashboard")
    public String getStudentDashboard(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        Student student = studentService.getStudentProfile(username);

        System.out.println(student);


        if (!studentRepo.existsByUsername(username)) {
            model.addAttribute("student", student);
            model.addAttribute("welcomeMessage", "Welcome to our App! \n Please fill your data!");
            return "student/profile";
        }

        model.addAttribute("student", student);
        return "student/dashboard";
    }

    @RequestMapping("/student/profile")
    public String getStudentProfile(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.getStudentProfile(username);

        if (!studentRepo.existsByUsername(username)) {
            student = new Student();
            student.setUsername(username);
        }
        model.addAttribute("student", student);
        return "student/profile";
    }

    @RequestMapping("/student/save")
    public String saveProfile(@ModelAttribute("student") Student student, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userDAO.findByUsername(username);

        if (optionalUser.isEmpty()) {

            model.addAttribute("errorMessage", "User not found!");
            return "student/profile";
        }

        User user = optionalUser.get();

        student.setUser(user);
        student.setUsername(user.getUsername());

        studentService.saveProfile(student);
        model.addAttribute("successMessage", "Profile saved successfully!");
        return "student/dashboard";
    }

    @RequestMapping("/student/traineeship_positions")
    public String listPositions(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student currentStudent = studentService.getStudentProfile(username);

        Optional<TraineePosition> assignedPosition = traineePositionRepo.findByApplicant_UsernameAndIsAssignedTrue(username);
        if(assignedPosition.isPresent()) {
            model.addAttribute("availableTraineeships", List.of(assignedPosition.get()));
            model.addAttribute("hasAssignment", true);
        }else {

            List<TraineePosition> positions = studentService.getAllTraineeships();
            List<Application> studentApplications = applicationRepo.findByApplicant_Username(username);

            Set<Integer> appliedPositionIds = new HashSet<>();
            for (Application app : studentApplications) {
                appliedPositionIds.add(app.getPosition().getPositionId());
            }


            model.addAttribute("availableTraineeships", positions);
            model.addAttribute("hasAssignment", false);
            model.addAttribute("appliedPositionIds", appliedPositionIds);
        }

        model.addAttribute("currentStudent", currentStudent);
        return "student/traineeship_positions";

    }

    @RequestMapping("/student/applyToTraineeship")
    public String applyToTraineeship(@RequestParam(value = "selected_position_id", required = false) Integer position_id, Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        studentService.applyToTraineeship(username, position_id);
        model.addAttribute("successMessage", "Application submitted successfully!");

        return "redirect:/student/traineeship_positions";
    };

    @RequestMapping("/student/logbook")
    public String showLogbookPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<TraineePosition> assignedPosition = traineePositionRepo.findByApplicant_UsernameAndIsAssignedTrue(username);

        assignedPosition.ifPresent(position -> model.addAttribute("traineePosition", position));
        return "student/logbook";
    }

    @PostMapping("/student/saveLogbook")
    public String saveLogbook(@ModelAttribute("traineePosition") TraineePosition traineePosition, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean success = studentService.saveLogbook(username, traineePosition.getStudentLogBook());

        if (success) {
            model.addAttribute("successMessage", "Logbook updated successfully!");
        } else {
            model.addAttribute("errorMessage", "No assigned traineeship found.");
        }

        Optional<TraineePosition> assignedPosition = traineePositionRepo.findByApplicant_UsernameAndIsAssignedTrue(username);
        assignedPosition.ifPresent(position -> model.addAttribute("traineePosition", position));

        return "student/logbook";
    }

    
}
