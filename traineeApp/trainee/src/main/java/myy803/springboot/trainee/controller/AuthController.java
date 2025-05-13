package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.model.Role;
import myy803.springboot.trainee.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        Student student = new Student();
        student.setRole(Role.STUDENT); // Ορίζουμε τον ρόλο
        model.addAttribute("user", student);
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") Student user, Model model) {
        try {
            if (userService.isUserPresent(user)) {
                model.addAttribute("errorMessage", "Το όνομα χρήστη υπάρχει ήδη!");
                return "auth/signup";
            }
            userService.saveUser(user);
            model.addAttribute("successMessage", "Επιτυχής εγγραφή!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Σφάλμα κατά την εγγραφή: " + e.getMessage());
            return "auth/signup";
        }
        return "auth/signin";
    }
}
