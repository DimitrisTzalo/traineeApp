package myy803.springboot.trainee.model;

import jakarta.persistence.*;

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

    @Column(name = "motivation", nullable = false)
    private int motivation;

    @Column(name = "effectiveness", nullable = false)
    private int effectiveness;

    @Column(name = "efficiency", nullable = false)
    private int efficiency;

    private EvaluationType evaluationType;

    public Evaluation() {}

    public Integer getEvaluationId() { return evaluationId; }
    public void setEvaluationId(Integer evaluationId) { this.evaluationId = evaluationId; }

    public TraineePosition getTraineePosition() { return traineePosition; }
    public void setTraineePosition(TraineePosition traineePosition) { this.traineePosition = traineePosition; }

    public int getMotivation() { return motivation; }
    public void setMotivation(int motivation) { this.motivation = motivation; }

    public int getEffectiveness() { return effectiveness; }
    public void setEffectiveness(int effectiveness) { this.effectiveness = effectiveness; }

    public int getEfficiency() { return efficiency; }
    public void setEfficiency(int efficiency) { this.efficiency = efficiency; }
}