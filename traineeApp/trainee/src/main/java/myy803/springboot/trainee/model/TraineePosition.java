package myy803.springboot.trainee.model;

import java.time.LocalDate;
import java.util.List;
import myy803.springboot.trainee.model.Company;
//import myy803.springboot.trainee.model.Evaluation;
import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.Student;

import jakarta.persistence.*;

@Entity
@Table(name = "trainee_positions")
public class TraineePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Integer positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_username")
    private Student applicant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_username")
    private Professor supervisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "committee_username", referencedColumnName = "username")
    private Committee committee;

    //@OneToMany(mappedBy = "traineePosition", cascade = CascadeType.ALL)
    //private List<Evaluation> evaluations;

    @Column(name = "position_title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "location", nullable = false)
    private String location;

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


    public TraineePosition() {}

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public Student getApplicant() { return applicant; }
    public void setApplicant(Student applicant) { this.applicant = applicant; }

    public Professor getSupervisor() { return supervisor; }
    public void setSupervisor(Professor professor) { this.supervisor = supervisor; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    //public List<Evaluation> getEvaluations() { return evaluations; }
    //public void setEvaluations(List<Evaluation> evaluations) { this.evaluations = evaluations; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTopics() { return topics; }
    public void setTopics(String topics) { this.topics = topics; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public boolean isAssigned() { return isAssigned; }
    public void setAssigned(boolean isAssigned) { this.isAssigned = isAssigned; }

    public String getStudentLogBook() { return studentLogBook;}

}
