package linhdoan.assessmentScheduling.assessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledAssessmentRepository extends JpaRepository<ScheduledAssessment, Integer> {
    List<ScheduledAssessment> findScheduledAssessmentsByStudent_StudentId(Integer studentId);
    void deleteScheduledAssessmentsByStudent_StudentId(Integer studentId);
}
