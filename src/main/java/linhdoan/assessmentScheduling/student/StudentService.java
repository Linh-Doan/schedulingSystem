package linhdoan.assessmentScheduling.student;

import linhdoan.assessmentScheduling.assessment.ScheduledAssessment;
import linhdoan.assessmentScheduling.assessment.ScheduledAssessmentRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private ScheduledAssessmentRepository scheduledAssessmentRepository;

    private static int SMALL_ASSESSMENT_TIME = 7;
    private static int MEDIUM_ASSESSMENT_TIME = 14;
    private static int LARGE_ASSESSMENT_TIME = 21;

    @Autowired
    public StudentService(StudentRepository studentRepository, ScheduledAssessmentRepository scheduledAssessmentRepository) {
        this.studentRepository = studentRepository;
        this.scheduledAssessmentRepository = scheduledAssessmentRepository;
    }

    @Transactional
    public List<ScheduledAssessment> getScheduleById(Integer studentId) throws Exception {
        List<ScheduledAssessment> schedule = new ArrayList<>();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student;
        if (!optionalStudent.isPresent()) {
            student = new Student(studentId);
            studentRepository.save(student);
        } else {
            student = optionalStudent.get();
        }
        try {
            URL url = new URL("http://localhost:8080/student/" + studentId + "/schedule");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                student.removeSchedule();
                scheduledAssessmentRepository.deleteScheduledAssessmentsByStudent_StudentId(studentId);
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder jsonBody = new StringBuilder();
                while (scanner.hasNext()) {
                    jsonBody.append(scanner.nextLine());
                }
                JSONArray assessments = new JSONArray(jsonBody.toString());
                for (int i = 0; i < assessments.length(); i++) {
                    JSONObject jsonAssessment = assessments.getJSONObject(i);
                    ScheduledAssessment scheduledAssessment = extractScheduledAssessmentFromJson(jsonAssessment);
                    scheduledAssessment.setStudent(student);
                    student.addToSchedule(scheduledAssessment);
                    scheduledAssessmentRepository.save(scheduledAssessment);
                    schedule.add(scheduledAssessment);
                }
            } else if (connection.getResponseCode() == HttpStatus.BAD_REQUEST.value()) {
                throw new IllegalStateException(connection.getResponseMessage());
            }
            return schedule;
        } catch (ConnectException e) {
            return scheduledAssessmentRepository.findScheduledAssessmentsByStudent_StudentId(studentId);
        }
    }

    protected ScheduledAssessment extractScheduledAssessmentFromJson(JSONObject jsonObject) {
        Integer assessmentId = jsonObject.getInt("assessmentId");
        String unitCode = jsonObject.getJSONObject("offering").getJSONObject("unit").getString("unitCode");
        Float assessmentWeight = jsonObject.getFloat("assessmentWeight");
        String dueDateString = jsonObject.getString("assessmentDate");
        LocalDate dueDate = LocalDate.parse(dueDateString, DateTimeFormatter.ofPattern("yyyy-MM-d"));
        LocalDate startDate = getStartDate(assessmentWeight, dueDate);
        return new ScheduledAssessment(assessmentId, unitCode, assessmentWeight, dueDate, startDate);
    }

    protected LocalDate getStartDate(Float assessmentWeight, LocalDate dueDate) {
        LocalDate startDate = null;
        if (assessmentWeight <= 0.1f) {
            startDate = dueDate.minusDays(SMALL_ASSESSMENT_TIME);
        } else if (assessmentWeight <= 0.2f) {
            startDate = dueDate.minusDays(MEDIUM_ASSESSMENT_TIME);
        } else if (assessmentWeight <= 1f) {
            startDate = dueDate.minusDays(LARGE_ASSESSMENT_TIME);
        }
        return startDate;
    }
}
