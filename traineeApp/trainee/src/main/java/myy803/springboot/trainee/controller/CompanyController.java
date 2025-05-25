package myy803.springboot.trainee.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.repository.*;
import myy803.springboot.trainee.service.ApplicationService;
import myy803.springboot.trainee.service.EvaluationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import myy803.springboot.trainee.service.CompanyService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    TraineePositionRepo traineePositionRepo;

    @Autowired
    EvaluationRepo evaluationRepo;

    @Autowired
    private EvaluationService evaluationService;

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

    @RequestMapping("/company/positions")
    public String getCompanyPositions(@ModelAttribute("traineePosition") TraineePosition traineePosition, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TraineePosition> positions = companyService.getCompanyPositions(username);
        model.addAttribute("positions", positions);


        List<Evaluation> evaluations = evaluationRepo.findByCompany_Username(username);

        List<Integer> evaluatedPositions = evaluations.stream()
                .map(e -> e.getTraineePosition().getPositionId())
                .toList();
        model.addAttribute("evaluatedPositions", evaluatedPositions);



        if (traineePosition.getPositionId() != null) {
            Optional<TraineePosition> existing = traineePositionRepo.findById(traineePosition.getPositionId());
            if (existing.isPresent()) {
                model.addAttribute("traineePosition", existing.get());
                model.addAttribute("editMode", true);
            }
        } else {
            model.addAttribute("traineePosition", new TraineePosition());
            model.addAttribute("editMode", false);
        }
        System.out.println("aaa"+ getClass().getResource("/templates/evaluate_traineeship.html"));


        return "company/positions";
    }


    @RequestMapping("/company/add_position")
    public String addOrUpdatePosition(@ModelAttribute("traineePosition") TraineePosition traineePosition, BindingResult bindingResult, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        if (traineePosition.getFromDate() != null && traineePosition.getToDate() != null &&
                traineePosition.getToDate().isBefore(traineePosition.getFromDate())) {
            bindingResult.rejectValue("toDate", "error.toDate", "End date cannot be before start date.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("positions", companyService.getCompanyPositions(username));
            model.addAttribute("editMode", traineePosition.getPositionId() != null);
            return "company/positions"; // Show form again with error
        }
        if (traineePosition.getPositionId() != null) {
            Optional<TraineePosition> existing = traineePositionRepo.findById(traineePosition.getPositionId());
            if (existing.isPresent()) {
                TraineePosition existingPosition = existing.get();
                existingPosition.setTitle(traineePosition.getTitle());
                existingPosition.setDescription(traineePosition.getDescription());
                existingPosition.setFromDate(traineePosition.getFromDate());
                existingPosition.setToDate(traineePosition.getToDate());
                existingPosition.setLocation(traineePosition.getLocation());
                existingPosition.setSkills(traineePosition.getSkills());
                existingPosition.setTopics(traineePosition.getTopics());
                existingPosition.setCompany(companyService.getCompanyProfile(username));
                traineePositionRepo.save(existingPosition);
            }
        } else {
            traineePosition.setCompany(companyService.getCompanyProfile(username));
            companyService.addPosition(username, traineePosition);
        }

        return "redirect:/company/positions";
    }



    @RequestMapping("/company/delete_position")
    public String deletePosition(@ModelAttribute("position") TraineePosition traineePosition, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        companyService.deletePosition(username, traineePosition);
        model.addAttribute("successMessage", "Successfully deleted position!");

        return "redirect:/company/positions";
    }

    @RequestMapping("/company/assigned_positions")
    public String getAssignedPositions(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TraineePosition> assignedPositions = traineePositionRepo.findBySupervisor_UsernameNotNullAndIsAssignedTrue();
        model.addAttribute("assignedPositions", assignedPositions);
        return "company/assigned_positions";
    }

    @RequestMapping("/company/evaluate_traineeship")
    public String evaluateTraineeship(@ModelAttribute("evaluation") Evaluation evaluation, Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Integer positionId = (evaluation.getTraineePosition() != null) ? evaluation.getTraineePosition().getPositionId() : null;
        if (positionId == null) {
            return "redirect:/company/positions";
        }


        Optional<Evaluation> existing = evaluationRepo.findByTraineePosition_PositionIdAndCompany_Username(positionId, username);

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            companyService.saveOrUpdateEvaluation(evaluation, username);
            return "redirect:/company/positions";
        }


        if (existing.isPresent()) {
            model.addAttribute("evaluation", existing.get());
        } else {
            model.addAttribute("evaluation", evaluation);
        }

        return "company/evaluate_traineeship";
    }



}
