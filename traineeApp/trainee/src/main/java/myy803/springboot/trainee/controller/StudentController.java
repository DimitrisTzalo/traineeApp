package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.service.UserService;
import myy803.springboot.trainee.service.StudentService;

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


        if (!studentRepo.existsByUsername(username)) {
            model.addAttribute("student", student);
            return "student/profile";
        }

        model.addAttribute("student", student);
        return "student/dashboard";
    }

    @RequestMapping("/student/profile")
    public String getStudentProfile(Model model) {
        model.addAttribute("student", new Student());
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
        student.setUsername(user.getUsername()); // mallon thelei join




        studentService.saveProfile(student);
        return "student/dashboard";
    }

}
