package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.service.StudentService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    StudentService studentService;

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
        List<TraineePosition> availableTraineeships = studentService.getAllTraineeships();
        System.out.println("### Found " + availableTraineeships.size() + " trainee positions");

        if(availableTraineeships.isEmpty()) {
            System.out.println("### No available traineeships");
            model.addAttribute("errorMessage", "No available traineeships at the moment.");
        } else {
            model.addAttribute("successMessage", "Available traineeships:");
        }

        model.addAttribute("availableTraineeships", availableTraineeships);
        return "student/traineeship_positions";
    }

    @RequestMapping("/student/applyToTraineeship")
    public String applyToTraineeship(@RequestParam(value = "selected_position_id", required = false) Integer position_id, Model model) {
        if (position_id == null) {
            model.addAttribute("errorMessage", "No traineeship position was selected.");
            return "student/traineeship_positions"; // Redirect to re-render the list
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        studentService.applyToTraineeship(username, position_id); //to id na einai tou traineeship
        model.addAttribute("message", "Application submitted successfully!");

        return "redirect:/student/traineeship_positions";
    };


}
