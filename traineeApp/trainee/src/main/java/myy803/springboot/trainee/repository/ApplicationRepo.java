package myy803.springboot.trainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.springboot.trainee.model.Application;

import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application, String> {

    Optional<Application> findByApplicant_Username(String username);

    boolean existsByApplicant_Username(String username);
}
