package myy803.springboot.trainee.controller;


import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.repository.ProfessorRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.service.ProfessorService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfessorController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ProfessorRepo professorRepo;

    @Autowired
    ProfessorService professorService;

    @RequestMapping("/professor/dashboard")
    public String getProfessorDashboard(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Professor professor = professorService.getProfessorProfile(username);


        if (!professorRepo.existsByUsername(username)) {
            model.addAttribute("professor", professor);
            model.addAttribute("welcomeMessage", "Welcome to our App! \n Please fill your data!");
            return "professor/profile";
        }

        professorService.saveProfile(professor);
        model.addAttribute("professor", professor);
        return "professor/dashboard";
    }

    @RequestMapping("/professor/profile")
    public String getProfessorProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor professor = professorService.getProfessorProfile(username);

        if (!professorRepo.existsByUsername(username)) {
            professor = new Professor();
            professor.setUsername(username);
            model.addAttribute("professor", professor);
            return "professor/profile";
        }

        model.addAttribute("professor", professor);
        return "professor/profile";
    }

    @RequestMapping("/professor/save")
    public String saveProfile(@ModelAttribute("professor") Professor professor, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userDAO.findByUsername(username);

        if (optionalUser.isEmpty()) {

            model.addAttribute("errorMessage", "User not found!");
            return "professor/profile";
        }

        User user = optionalUser.get();

        professor.setUser(user);
        professor.setUsername(user.getUsername());

        professorService.saveProfile(professor);
        model.addAttribute("successMessage", "Profile saved successfully!");
        return "professor/dashboard";
    }
}
