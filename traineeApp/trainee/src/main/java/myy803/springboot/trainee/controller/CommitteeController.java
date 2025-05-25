package myy803.springboot.trainee.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import myy803.springboot.trainee.formsdata.AssignProfessorForm;
import myy803.springboot.trainee.formsdata.AssignStudentForm;
import myy803.springboot.trainee.formsdata.SearchForm;
import myy803.springboot.trainee.formsdata.SearchProfessorForm;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import myy803.springboot.trainee.service.CommitteeService;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private ProfessorRepo professorRepo;
    @Autowired
    private TraineePositionRepo traineePositionRepo;

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
            System.out.println("Results: " + results);
        }

        return "committee/match_student";
    }

    @RequestMapping("/committee/assign_position")
    public String assignStudentPosition(@ModelAttribute AssignStudentForm assignStudentForm, Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //COMMITTEE

        Integer positionId = assignStudentForm.getPositionId();
        String studentUsername = assignStudentForm.getStudentUsername();

        Optional<Student> selectedStudent = studentRepo.findByUsername(studentUsername);

        boolean hasApplied = applicationRepo.existsByApplicant_UsernameAndPosition_PositionId(studentUsername, positionId);

        if(!hasApplied) {
            model.addAttribute("errorMessage", "Student has not applied for this position.");
            return "committee/match_student";
        }

        committeeService.assignPositiontoStudent(username, positionId, studentUsername);
        model.addAttribute("successMessage", selectedStudent.get().getStudentName()+ "has assigned to position " + positionId + " successfully!");


        return "redirect:/committee/dashboard";
    }

    @RequestMapping("/committee/search_professor")
    public String searchProfessor(@ModelAttribute SearchProfessorForm searchProfessorForm, Model model) {
        List<TraineePosition> assignedPositions = traineePositionRepo.findByIsAssignedTrueAndSupervisorIsNull();
        model.addAttribute("allPositions", assignedPositions);
        model.addAttribute("searchProfessorForm", searchProfessorForm);

        if (searchProfessorForm.getSelectedPosition() != null) {
            Integer positionId = searchProfessorForm.getSelectedPosition();

            Optional<TraineePosition> selected = assignedPositions.stream()
                    .filter(p -> p.getPositionId().equals(positionId))
                    .findFirst();

            selected.ifPresent(p -> model.addAttribute("selectedPosition", p));

            if (searchProfessorForm.getCriteria() != null && selected.isPresent()) {
                List<Professor> results = committeeService.searchProfessor(positionId, searchProfessorForm.getCriteria());
                model.addAttribute("resultsProfessors", results);
            }
        }

        return "committee/search_professor";
    }

    @RequestMapping("/committee/assign_professor")
    public String assignProfessorPosition(@ModelAttribute AssignProfessorForm assignProfessorForm, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); //COMMITTEE

        Integer positionId = assignProfessorForm.getPositionId();
        String professorUsername = assignProfessorForm.getProfessorUsername();

        Optional<Professor> selectedProfessor = professorRepo.findByUsername(professorUsername);

        boolean hasSupervised = traineePositionRepo.existsBySupervisor_UsernameAndPositionId(professorUsername, positionId);

        if (hasSupervised) {
            model.addAttribute("errorMessage", "Professor is supervising this position.");
            model.addAttribute("searchForm", new SearchForm()); // Ensure form exists
            model.addAttribute("allProfessors", professorRepo.findAll());
            return "committee/search_professor";
        }

        committeeService.assignPositiontoProfessor(username, positionId, professorUsername);
        model.addAttribute("successMessage", selectedProfessor.get().getProfessorName()+ "has supervised to position " + positionId + " successfully!");

        return "redirect:/committee/search_professor";
    }

    @RequestMapping("/committee/positions_in_progress")
    public String getPositionsInProgress(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TraineePosition> positionsInProgress = traineePositionRepo.findByCommittee_UsernameAndIsAssignedTrue(username);
        model.addAttribute("positionsInProgress", positionsInProgress);

        return "committee/positions_in_progress";
    }


}
