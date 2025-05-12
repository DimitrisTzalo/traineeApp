package myy803.springboot.trainee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.springboot.trainee.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
}
