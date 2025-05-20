package myy803.springboot.trainee.model;


import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @Column(nullable = false, name="application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer application_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private Student applicant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private TraineePosition position;


    public Application() {super();}

    public Application(Student applicant, TraineePosition position) {
        super();
        this.applicant = applicant;
        this.position = position;
    }

    public Integer getApplication_id() {return application_id;}
    public void setApplication_id(Integer application_id) {this.application_id = application_id;}

    public Student getApplicant() {return applicant;}
    public void setApplicant(Student applicant) {this.applicant = applicant;}

    public TraineePosition getPosition() {return position;}
    public void setPosition(TraineePosition position) {this.position = position;}

}
