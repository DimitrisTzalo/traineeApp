package myy803.springboot.trainee.DTO;

import myy803.springboot.trainee.model.Role;

public class RegisterForm {
    private String username;
    private String password;
    private Role role;

    // Getters and Setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}
}
