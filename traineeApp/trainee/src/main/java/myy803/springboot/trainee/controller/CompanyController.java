package myy803.springboot.trainee.controller;

import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.model.User;
import myy803.springboot.trainee.repository.CompanyRepo;
import myy803.springboot.trainee.repository.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.model.Company;
import myy803.springboot.trainee.service.CompanyService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class CompanyController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    CompanyService companyService;

    @RequestMapping("/company/dashboard")
    public String getCompanyDashboard(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Company company = companyService.getCompanyProfile(username);


        if (!companyRepo.existsByUsername(username)) {
            model.addAttribute("company", company);
            model.addAttribute("welcomeMessage", "Welcome to our App! \n Please fill your data!");
            return "company/profile";
        }

        model.addAttribute("company", company);
        return "company/dashboard";
    }

    @RequestMapping("/company/profile")
    public String getCompanyProfile(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyService.getCompanyProfile(username);

        if (!companyRepo.existsByUsername(username)) {
            company = new Company();
            company.setUsername(username);
        }

        model.addAttribute("company", company);
        return "company/profile";
    }

    @RequestMapping("/company/save")
    public String saveCompanyProfile(@ModelAttribute("company") Company company, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userDAO.findByUsername(username);

        if(optionalUser.isEmpty()) {

            model.addAttribute("errorMessage", "User not found!");
            return "company/profile";
        }

        User user = optionalUser.get();

        company.setUser(user);
        company.setUsername(username);

        companyService.saveProfile(company);
        model.addAttribute("successMessage", "Profile saved successfully!");
        return "company/dashboard";
    }


}
