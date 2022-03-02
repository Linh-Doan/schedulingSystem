package linhdoan.assessmentScheduling.assessment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.assessmentScheduling.student.Student;

import javax.persistence.*;
import java.time.LocalDate;
@Entity(name = "ScheduledAssessment")
@Table(
        name = "scheduled_assessment",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_scheduled_assessment", columnNames = {"assessment_id", "student_id"})
        }
)
public class ScheduledAssessment {
    @Id
    @SequenceGenerator(
            name = "scheduled_assessment_id_sequence",
            sequenceName = "scheduled_assessment_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scheduled_assessment_id_sequence"
    )
    @Column(
            name = "scheduled_assessment_id",
            updatable = false
    )
    private Integer scheduledAssessmentId;

    @Column(
            name = "assessment_id",
            nullable = false
    )
    private Integer assessmentId;

    @ManyToOne
    @JoinColumn(
            name = "student_id"
    )
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"schedule"})
    private Student student;

    @Column(
            name = "unit_code",
            nullable = false
    )
    private String unitCode;

    @Column(
            name = "assessment_weight",
            nullable = false
    )
    private Float assessmentWeight;

    @Column(
            name = "start_date"
    )
    private LocalDate startDate;

    @Column(
            name = "due_date",
            nullable = false
    )
    private LocalDate dueDate;

    public ScheduledAssessment() {
    }

    public ScheduledAssessment(Integer assessmentId, String unitCode, Float assessmentWeight, LocalDate dueDate, LocalDate startDate) {
        this.assessmentId = assessmentId;
        this.unitCode = unitCode;
        this.assessmentWeight = assessmentWeight;
        this.dueDate = dueDate;
        this.startDate = startDate;
    }

    public Integer getScheduledAssessmentId() {
        return scheduledAssessmentId;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Float getAssessmentWeight() {
        return assessmentWeight;
    }

    public void setAssessmentWeight(Float assessmentWeight) {
        this.assessmentWeight = assessmentWeight;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate finishDate) {
        this.dueDate = finishDate;
    }
}
