package myy803.springboot.trainee.controller;

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

@Controller
public class StudentController {

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/student/dashboard")
    public String getStudentDashboard() {
        return "student/dashboard";
    }

    @RequestMapping("/student/profile")
    public String getStudentProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentService.getStudentProfile(username);
        model.addAttribute("student", student);
        return  "student/profile";
    }

    @RequestMapping("/student/save")
    public String saveProfile(@ModelAttribute("student") Student student, Model model) {

        studentService.saveProfile(student);

        return "student/dashboard";
    }
}
