package linhdoan.assessmentScheduling.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.assessmentScheduling.assessment.ScheduledAssessment;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity (name = "Student")
@Table (name = "student")
public class Student {
    @Id
    @Column(
            name = "student_id",
            updatable = false
    )
    private Integer studentId;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"student"})
    private List<ScheduledAssessment> schedule;

    public Student() {
    }

    public Student(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    private List<ScheduledAssessment> getScheduleInternal() {
        if (schedule == null) {
            schedule = new ArrayList<>();
        }
        return schedule;
    }

    public void removeSchedule() {
        getScheduleInternal().forEach(scheduledAssessment -> scheduledAssessment.setStudent(null));
        getScheduleInternal().clear();
    }

    public List<ScheduledAssessment> getSchedule() {
        List<ScheduledAssessment> sortedSchedulesAssessments = new ArrayList<>(getScheduleInternal());
        PropertyComparator.sort(sortedSchedulesAssessments, new MutableSortDefinition("startDate", true, true));
        return Collections.unmodifiableList(sortedSchedulesAssessments);
    }

    public void addToSchedule(ScheduledAssessment scheduledAssessment) {
        getScheduleInternal().add(scheduledAssessment);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", schedule=" + schedule +
                '}';
    }
}
