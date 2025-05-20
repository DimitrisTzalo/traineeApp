package myy803.springboot.trainee.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="user_name", unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
    @Enumerated(EnumType.STRING)
    @Column(name="role")
	private Role role;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Student student;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Company company;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Professor professor;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Committee committee;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
	     return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public int getId() {
		return id;
	}

	public Role getRole() {
		return role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		student.setUser(this); // Ensure bidirectional relationship
	}

	public Company getCompany() {return company;}
	public void setCompany(Company company) {
		this.company = company;
		company.setUser(this);
	}

	public Professor getProfessor() {return professor;}
	public void setProfessor(Professor professor) {
		this.professor = professor;
		professor.setUser(this);
	}

	public Committee getCommittee() {return committee;}
	public void setCommittee(Committee committee) {
		this.committee = committee;
		committee.setUser(this);
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
