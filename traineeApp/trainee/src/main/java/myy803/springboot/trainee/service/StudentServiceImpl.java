package myy803.springboot.trainee.service;

import myy803.springboot.trainee.model.Student;
import myy803.springboot.trainee.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public void saveProfile(Student student) {
        studentRepo.save(student);
    }
    /*
    @Override
    public boolean isUserPresent(User user) {
        Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
        return storedUser.isPresent();
    }

    // Method defined in Spring Security UserDetailsService interface
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // orElseThrow method of Optional container that throws an exception if Optional result  is null
        return userDAO.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND %s", username)
                ));
    }

     */
}

