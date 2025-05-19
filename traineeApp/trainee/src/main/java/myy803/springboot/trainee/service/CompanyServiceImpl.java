package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Company;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.CompanyRepo;
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


}
