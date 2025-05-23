package myy803.springboot.trainee.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false, name = "student_id")
    private Integer student_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(nullable = false, name = "student_name")
    private String studentName;

    @Column(nullable = false, name = "student_AM")
    private String studentAM;

    @Column(name = "average_grade")
    private String averageGrade;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @Column(name = "interests")
    private String interests;

    @Column(name = "skills")
    private String skills;

    @Column(name = "looking_for_traineeship")
    private boolean lookingForTraineeship;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private TraineePosition traineePosition;

    public Student() {
        super();
    }
    public Student(String username) {
        this.username = username;
    }


    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Integer getStudentId() {return student_id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getStudentName() {return studentName;}
    public void setStudentName(String studentName) {this.studentName = studentName;}

    public String getStudentAM() {return studentAM;}
    public void setStudentAM(String studentAM) {this.studentAM = studentAM;}

    public String getAverageGrade() {return averageGrade;}
    public void setAverageGrade(String averageGrade) {this.averageGrade = averageGrade;}

    public String getPreferredLocation() {return preferredLocation;}
    public void setPreferredLocation(String preferredLocation) {this.preferredLocation = preferredLocation;}

    public String getInterests() {return interests;}
    public void setInterests(String interests) {this.interests = interests;}

    public String getSkills() {return skills;}
    public void setSkills(String skills) {this.skills = skills;}

    public boolean isLookingForTraineeship() {return lookingForTraineeship;}
    public void setLookingForTraineeship(boolean lookingForTraineeship) {this.lookingForTraineeship = lookingForTraineeship;}

    public List<Application> getApplications() {return applications;}
    public void setApplications(List<Application> applications) {this.applications = applications;}

    public TraineePosition getTraineePosition() {return traineePosition;}
    public void setTraineePosition(TraineePosition traineePosition) {this.traineePosition = traineePosition;}

    public void addApplication(Application application) {applications.add(application); }

    public List<String> getInterestList() {return interests != null ? List.of(interests.split(",")) : List.of();}
}
