package myy803.springboot.trainee.service;


import jakarta.transaction.Transactional;
import myy803.springboot.trainee.model.*;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.CompanyRepo;
import myy803.springboot.trainee.repository.EvaluationRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private TraineePositionRepo traineePositionRepo;
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private EvaluationRepo evaluationRepo;

    @Override
    public void saveProfile(Company company) {
        Optional<Company> existing = companyRepo.findByUsername(company.getUsername());

        if (existing.isPresent()) {
            Company existingCompany = existing.get();
            existingCompany.setCompanyName(company.getCompanyName());
            existingCompany.setCompanyLocation(company.getCompanyLocation());
            existingCompany.setCompanyPositions(company.getCompanyPositions());

            companyRepo.save(existingCompany);
        } else {
            companyRepo.save(company);
        }
    }

    @Override
    public Company getCompanyProfile(String username) {
        return companyRepo.findByUsername(username).orElse(new Company(username));
    }

    @Override
    public List<TraineePosition> getCompanyPositions(String username) {
        List<TraineePosition> positions = new ArrayList<>();
        Optional<Company> company = companyRepo.findByUsername(username);
        if (company.isPresent()) {
            positions = company.get().getCompanyPositions();
        }
        return positions;
    }

    @Override
    public void addPosition(String username, TraineePosition position) {
       Optional<Company> company = companyRepo.findByUsername(username);
        if (company.isPresent()) {
            position.setCompany(company.get());
            traineePositionRepo.save(position);
        }

    }

    @Transactional
    @Override
    public void deletePosition(String username, TraineePosition position) {

        applicationRepo.deleteAll(applicationRepo.findByPosition_PositionId(position.getPositionId()));
        evaluationRepo.deleteAll(evaluationRepo.findByTraineePosition_PositionId(position.getPositionId()));

        position.setApplicant(null);
        position.setSupervisor(null);
        position.setCompany(null);
        position.setCommittee(null);

        traineePositionRepo.flush();

        System.out.println("✅ Deleting: " + position.getPositionId());
        traineePositionRepo.delete(position); // ΕΔΩ γίνεται πραγματικό delete
    }





    @Override
    public void saveOrUpdateEvaluation(Evaluation evalForm, String username) {
        Integer posId = evalForm.getTraineePosition().getPositionId();
        if (posId == null) return;

        TraineePosition position = traineePositionRepo.findById(posId).orElse(null);
        Company company = companyRepo.findByUsername(username).orElse(null);

        if (position == null || company == null) return;

        Evaluation evaluation = evaluationRepo
                .findByTraineePosition_PositionIdAndCompany_Username(posId, username)
                .orElse(new Evaluation());

        evaluation.setTraineePosition(position);
        evaluation.setCompany(company);
        evaluation.setEvaluationType(EvaluationType.COMPANY);

        evaluation.setMotivation(evalForm.getMotivation());
        evaluation.setEffectiveness(evalForm.getEffectiveness());
        evaluation.setEfficiency(evalForm.getEfficiency());

        evaluationRepo.save(evaluation);
    }



}
