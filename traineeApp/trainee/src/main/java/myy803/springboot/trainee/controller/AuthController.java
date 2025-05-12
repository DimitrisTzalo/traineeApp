package myy803.springboot.trainee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.model.Role;
import myy803.springboot.trainee.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "auth/login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("role") String roleParam, Model model) {

       System.out.println("eeee");
        if (userService.isUserPresent(user)) {
            model.addAttribute("errorMessage", "User already registered!");
            return "auth/login";
        }

        userService.saveUser(user);

        model.addAttribute("successMessage", "User registered successfully!");
        //return "redirect:/student/dashboard";

        System.out.println("Role: " + user.getRole());

        return "auth/login";
    }
}
