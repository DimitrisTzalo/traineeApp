package myy803.springboot.trainee.model;

import jakarta.persistence.*;
import myy803.springboot.trainee.model.EvaluationType;
@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Integer evaluationId;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private TraineePosition traineePosition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "company_username", referencedColumnName = "username")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_username", referencedColumnName = "username")
    private Professor professor;

    @Column(name = "motivation")
    private int motivation;

    @Column(name = "effectiveness")
    private int effectiveness;

    @Column(name = "efficiency")
    private int efficiency;

    @Column(name = "facilities", nullable = false)
    private int facilities;

    @Column(name = "guidance", nullable = false)
    private int guidance;

    private EvaluationType evaluationType;

    public Evaluation() {}

    public Integer getEvaluationId() { return evaluationId; }
    public void setEvaluationId(Integer evaluationId) { this.evaluationId = evaluationId; }

    public TraineePosition getTraineePosition() { return traineePosition; }
    public void setTraineePosition(TraineePosition traineePosition) { this.traineePosition = traineePosition; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public int getMotivation() { return motivation; }
    public void setMotivation(int motivation) { this.motivation = motivation; }

    public int getEffectiveness() { return effectiveness; }
    public void setEffectiveness(int effectiveness) { this.effectiveness = effectiveness; }

    public int getEfficiency() { return efficiency; }
    public void setEfficiency(int efficiency) { this.efficiency = efficiency; }

    public EvaluationType getEvaluationType() { return evaluationType; }
    public void setEvaluationType(EvaluationType evaluationType) { this.evaluationType = evaluationType; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public int getFacilities() { return facilities; }
    public void setFacilities(int facilities) { this.facilities = facilities; }

    public int getGuidance() { return guidance; }
    public void setGuidance(int guidance) { this.guidance = guidance; }
}