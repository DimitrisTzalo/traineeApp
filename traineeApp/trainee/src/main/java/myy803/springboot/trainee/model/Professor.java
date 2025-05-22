package myy803.springboot.trainee.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "professors")
public class Professor {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false, name = "professor_id")
    private Integer professor_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "professor_id")
    private User user;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "professor_name")
    private String professorName;

    @Column(name = "interests")
    private String interests;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TraineePosition> supervisedPositions; // to counter to pairnw apto size ths listas

    public Professor() {super();}
    public Professor(String username) {this.username = username;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Integer getProfessor_id() {return professor_id;}
    public void setProfessor_id(Integer professor_id) {this.professor_id = professor_id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getProfessorName() {return professorName;}
    public void setProfessorName(String professorName) {this.professorName = professorName;}

    public String getInterests() {return interests;}
    public void setInterests(String interests) {this.interests = interests;}

    public List<TraineePosition> getSupervisedPositions() {return supervisedPositions;}
    public void setSupervisedPositions(List<TraineePosition> supervisedPositions) {this.supervisedPositions = supervisedPositions;}

    public List<String> getInterestList() {return interests != null ? List.of(interests.split(",")) : List.of();}
}
