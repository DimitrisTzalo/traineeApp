package myy803.springboot.trainee.service;

import org.springframework.beans.factory.annotation.Autowired;
import myy803.springboot.trainee.model.Application;
import myy803.springboot.trainee.repository.ApplicationRepo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Override
    public void save(Application application) {
        applicationRepo.save(application);
    }
}
