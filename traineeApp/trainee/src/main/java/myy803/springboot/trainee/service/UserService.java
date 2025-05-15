package myy803.springboot.trainee.service;

import org.springframework.stereotype.Service;

import myy803.springboot.trainee.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public boolean isUsernameTaken(String username);
}
