package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.DTO.RegisterForm;
import myy803.springboot.trainee.model.Role;
import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("form", new RegisterForm());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("form") RegisterForm form, Model model) {
        if (userService.isUsernameTaken(form.getUsername())) {
            model.addAttribute("errorMessage", "User already exists");
            return "auth/signup";
        }

        User user;

        switch (form.getRole()) {
            case STUDENT:
                user = new Student(); break;
            default:
                user = new Student(); // fallback
        }

        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setRole(form.getRole());
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully");

//        if (user.getRole() == Role.STUDENT) {
//            return "redirect:/student/complete-profile?userId=" + user.getId();
//        }

        return "redirect:/login";
    }
}
