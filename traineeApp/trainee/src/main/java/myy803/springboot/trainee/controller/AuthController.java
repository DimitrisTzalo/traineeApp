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


        if (userService.isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "auth/login";
        }
        System.out.println("eeee");
        System.out.println(user.getRole());
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role must not be null");
        }


        userService.saveUser(user);

        model.addAttribute("successMessage", "User registered successfully!");

        System.out.println("eeeewhat");


        return "auth/login";
    }
}
