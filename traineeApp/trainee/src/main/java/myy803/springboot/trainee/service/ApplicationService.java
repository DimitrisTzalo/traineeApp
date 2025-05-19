package myy803.springboot.trainee.service;


import myy803.springboot.trainee.model.Application;
import org.springframework.stereotype.Service;

@Service
public interface ApplicationService {

    void save(Application application);
}
