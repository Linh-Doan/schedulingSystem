package linhdoan.assessmentScheduling.student;

import linhdoan.assessmentScheduling.assessment.ScheduledAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "scheduling/{studentId}")
    public ResponseEntity getScheduleById(@PathVariable("studentId") Integer studentId) {
        List<ScheduledAssessment> schedule = null;
        try {
            schedule = studentService.getScheduleById(studentId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(schedule);
    }
}
