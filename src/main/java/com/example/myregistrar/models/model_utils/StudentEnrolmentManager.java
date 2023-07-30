package com.example.myregistrar.models.model_utils;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentEnrolmentManager {
    public boolean handleStudent(Student student, Course course) {
        CourseEnrolmentHandler ageHandler = new EligibilityCheckHandler();
        CourseEnrolmentHandler universityHandler = new UniversityAppliedCheckHandler();

        ageHandler.setNextHandler(universityHandler);
        return ageHandler.handleEnrolment(student, course);
    }
}
