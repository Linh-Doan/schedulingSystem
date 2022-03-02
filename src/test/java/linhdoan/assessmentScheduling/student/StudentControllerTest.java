package linhdoan.assessmentScheduling.student;
import linhdoan.assessmentScheduling.assessment.ScheduledAssessment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    private ScheduledAssessment assessment;
    private Student student;

    @Before
    public void setUp() {
        student = new Student(1);
        assessment = new ScheduledAssessment();
        assessment.setAssessmentId(1);
    }

    @Test
    public void givenStudentId_whenGetSchedule_thenReturnJsonArray() throws Exception {
        given(studentService.getScheduleById(student.getStudentId())).willReturn(List.of(assessment));
        mvc.perform(get("/scheduling/{studentId}", student.getStudentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].assessmentId").value(assessment.getAssessmentId()));
    }
}
