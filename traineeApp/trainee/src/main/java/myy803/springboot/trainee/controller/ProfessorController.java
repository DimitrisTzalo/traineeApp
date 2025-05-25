package myy803.springboot.trainee.controller;


import jakarta.servlet.http.HttpServletRequest;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.repository.EvaluationRepo;
import myy803.springboot.trainee.repository.ProfessorRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import myy803.springboot.trainee.repository.UserDAO;
import myy803.springboot.trainee.service.EvaluationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.service.ProfessorService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @Autowired
    private EvaluationRepo evaluationRepo;

    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @Autowired
    private EvaluationService evaluationService;

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

    @RequestMapping("/professor/supervised_positions")
    public String listSupervisedPositions(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TraineePosition> supervisedPositions = professorService.getSupervisedPositions(username);
        model.addAttribute("supervisedPositions", supervisedPositions);

        List<Evaluation> evaluations = evaluationRepo.findByProfessor_Username(username);

        List<Integer> evaluatedPositions = evaluations.stream()
                .map(e -> e.getTraineePosition().getPositionId())
                .toList();
        model.addAttribute("evaluatedPositions", evaluatedPositions);

        return "professor/supervised_positions";
    }

    @RequestMapping("/professor/evaluate_traineeship")
    public String evaluateTraineeship(@ModelAttribute("evaluation") Evaluation evaluation, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Integer positionId = (evaluation.getTraineePosition() != null) ? evaluation.getTraineePosition().getPositionId() : null;
        if (positionId == null) {
            return "redirect:/professor/supervised_positions";
        }

        Optional<Evaluation> existingEvaluation = evaluationRepo.findByTraineePosition_PositionIdAndProfessor_Username(positionId, username);

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            professorService.saveOrUpdateEvaluation(evaluation, username);
            return "redirect:/professor/supervised_positions";
        }
        if (existingEvaluation.isPresent()) {
            model.addAttribute("evaluation", existingEvaluation.get());
        } else {
            model.addAttribute("evaluation", evaluation);
        }

        return "professor/evaluate_traineeship";
    }

}