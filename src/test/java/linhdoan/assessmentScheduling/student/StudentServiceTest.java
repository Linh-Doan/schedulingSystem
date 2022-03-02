package linhdoan.assessmentScheduling.student;

import linhdoan.assessmentScheduling.assessment.ScheduledAssessment;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RunWith(SpringRunner.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService studentService;
    @Test
    public void testExtractScheduledAssessmentFromJson() throws Exception {
        ScheduledAssessment assessment = new ScheduledAssessment(1, "FIT1001", 0.3f, LocalDate.of(2022, Month.FEBRUARY, 1), LocalDate.of(2022, Month.JANUARY, 1));
        JSONObject unitJsonObject = new JSONObject();
        unitJsonObject.put("unitCode", assessment.getUnitCode());
        JSONObject offeringJsonObject = new JSONObject();
        offeringJsonObject.put("unit", unitJsonObject);
        unitJsonObject.put("unitCode", assessment.getUnitCode());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assessmentId", assessment.getAssessmentId());
        jsonObject.put("offering", offeringJsonObject);
        jsonObject.put("assessmentWeight", assessment.getAssessmentWeight());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        jsonObject.put("assessmentDate", assessment.getDueDate().format(formatter));
        ScheduledAssessment convertedAssessment = studentService.extractScheduledAssessmentFromJson(jsonObject);
        assertTrue(Objects.equals(assessment.getAssessmentId(), convertedAssessment.getAssessmentId()));
    }

    @Test
    public void givenSmallAssessment_testGetStartDate() throws Exception {
        LocalDate dueDate = LocalDate.of(2022, Month.FEBRUARY, 1);
        Float assessmentWeight = 0.1f;
        LocalDate startDate = studentService.getStartDate(assessmentWeight, dueDate);
        assertEquals(startDate, LocalDate.of(2022, Month.JANUARY, 25));
    }

    @Test
    public void givenLargeAssessment_testGetStartDate() throws Exception {
        LocalDate dueDate = LocalDate.of(2022, Month.FEBRUARY, 1);
        Float assessmentWeight = 0.5f;
        LocalDate startDate = studentService.getStartDate(assessmentWeight, dueDate);
        assertEquals(startDate, LocalDate.of(2022, Month.JANUARY, 11));
    }
}
