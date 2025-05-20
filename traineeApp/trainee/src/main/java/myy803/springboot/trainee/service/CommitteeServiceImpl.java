package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Committee;
import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.model.TraineePosition;
import myy803.springboot.trainee.repository.CommitteeRepo;
import myy803.springboot.trainee.repository.ApplicationRepo;
import myy803.springboot.trainee.repository.TraineePositionRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommitteeServiceImpl implements CommitteeService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CommitteeRepo committeeRepo;

    @Autowired
    private TraineePositionRepo traineePositionRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Override
    public void saveProfile(Committee committee) {
        Optional<Committee> existing = committeeRepo.findByUsername(committee.getUsername());

        if (existing.isPresent()) {
            Committee existingCommittee = existing.get();
            existingCommittee.setCommitteeName(committee.getCommitteeName());


            committeeRepo.save(existingCommittee);
        } else {
            committeeRepo.save(committee);
        }
    }

    @Override
    public Committee getCommitteeProfile(String username) {
        return committeeRepo.findByUsername(username).orElse(new Committee(username));
    }
}
