package myy803.springboot.trainee.model;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "student_name", nullable = true)
    private String studentName;

    @Column(name = "student_AM", nullable = true)
    private String studentAM;

    @Column(name = "average_grade", nullable = true)
    private String averageGrade;

    @Column(name = "preferred_location", nullable = true)
    private String preferredLocation;

    @Column(name = "interests", nullable = true)
    private String interests;

    @Column(name = "skills", nullable = true)
    private String skills;

    @Column(name = "looking_for_traineeship", nullable = true)
    private boolean lookingForTraineeship;

    // Getters and Setters
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



}
