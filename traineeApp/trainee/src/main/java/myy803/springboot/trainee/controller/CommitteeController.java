package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.formsdata.SearchForm;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.CommitteeRepo;
import myy803.springboot.trainee.repository.StudentRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.service.CommitteeService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@Controller
public class CommitteeController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    CommitteeRepo committeeRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    CommitteeService committeeService;
    @Autowired
    private ApplicationRepo applicationRepo;

    @RequestMapping("/committee/dashboard")
    public String getCommitteeDashboard(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        Committee committee = committeeService.getCommitteeProfile(username);


        if (!committeeRepo.existsByUsername(username)) {
            model.addAttribute("committee", committee);
            model.addAttribute("welcomeMessage", "Welcome to our App! \n Please fill your data!");
            return "committee/profile";
        }

        model.addAttribute("committee", committee);
        return "committee/dashboard";
    }

    @RequestMapping("/committee/profile")
    public String getCommitteeProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Committee committee = committeeService.getCommitteeProfile(username);


        if (!committeeRepo.existsByUsername(username)) {
           committee = new Committee();
           committee.setUsername(username);
        }

        model.addAttribute("committee", committee);
        return "committee/profile";
    }

    @RequestMapping("/committee/save")
    public String saveCommittee(@ModelAttribute("committee") Committee committee, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userDAO.findByUsername(username);

        if(optionalUser.isEmpty()) {

            model.addAttribute("errorMessage", "User not found!");
            return "student/profile";
        }

        User user = optionalUser.get();

        committee.setUser(user);
        committee.setUsername(user.getUsername());

        committeeService.saveProfile(committee);
        model.addAttribute("successMessage", "Profile saved successfully!");
        return "redirect:/committee/dashboard";
    }

    @RequestMapping("/committee/available_positions")
    public String listApplicants(Model model) {
        List<TraineePosition> availablePositions = committeeService.getAvailableTraineeships();
        List<Application> applications = committeeService.getApplicationsForAvailablePositions();

        model.addAttribute("availablePositions", availablePositions);
        model.addAttribute("applications", applications);

        return "committee/available_positions";

    }

    @RequestMapping("/committee/match_student")
    public String matchStudent(@ModelAttribute SearchForm searchForm, Model model) {
        String username = searchForm.getSelectedUsername();
        String criteria = searchForm.getCriteria();
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("username", username);

        if (searchForm.getSelectedUsername() != null && !searchForm.getSelectedUsername().isBlank()) {
            studentRepo.findByUsername(searchForm.getSelectedUsername())
                    .ifPresent(student -> model.addAttribute("selectedStudent", student));
        }

        if (criteria != null && !criteria.isBlank()) {
            List<TraineePosition> results = committeeService.searchForStudent(username, criteria);
            model.addAttribute("results", results);
        }

        return "committee/match_student";
    }
}
