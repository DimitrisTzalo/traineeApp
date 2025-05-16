package myy803.springboot.trainee.model;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "trainee_positions")
public class TraineePositions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Integer positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private Student applicantName;

    @Column(name = "position_title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "topics")
    private String topics;

    @Column(name = "skills")
    private String skills;

    @Column(name = "is_assigned", nullable = false)
    private boolean isAssigned;

    @Column(name = "student_log_book")
    private String studentLogBook;

    @Column(name = "pass_fail_grade", nullable = false)
    private boolean passFailGrade;


    public TraineePositions() {};

    public Integer getPositionId() {return this.positionId;}
    public void setPositionId(Integer position_id) {this.positionId = position_id;}

    public Student getApplicantName() {return this.applicantName;}
    public void setApplicantName(Student applicantName) {this.applicantName = applicantName;}

    public String getPositionTitle() {return this.title;}
    public void setPositionTitle(String title) {this.title = title;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDate getFromDate() {return this.fromDate;}
    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getToDate() {return this.toDate;}
    public void setToDate(LocalDate toDate) {this.toDate = toDate;}

    public String getTopics() {return this.topics;}
    public void setTopics(String topics) {this.topics = topics;}

    public String getSkills() {return this.skills;}
    public void setSkills(String skills) {this.skills = skills;}

    public boolean isAssigned() {return this.isAssigned;}
    public void setAssigned(boolean isAssigned) {this.isAssigned = isAssigned;}

    public String getStudentLogBook() {return this.studentLogBook;}
    public void setStudentLogBook(String studentLogBook) {this.studentLogBook = studentLogBook;}

    public boolean isPassFailGrade() {return this.passFailGrade;}
    public void setPassFailGrade(boolean passFailGrade) {this.passFailGrade = passFailGrade;}

}