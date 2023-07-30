package com.example.myregistrar.models.model_utils;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

import java.util.Optional;

public class UniversityAppliedCheckHandler implements CourseEnrolmentHandler {
    private Optional<CourseEnrolmentHandler> nextHandler = Optional.empty();
    @Override
    public void setNextHandler(CourseEnrolmentHandler nextHandler) {
        this.nextHandler = Optional.of(nextHandler);
    }

    @Override
    public boolean handleEnrolment(Student student, Course course) {
        boolean ok = student != null && student.getId() != null && student.getUniversity() != null && student.getUniversity().getId() != null
                && course != null && course.getId() != null && course.getUniversity() != null && course.getUniversity().getId() != null;

        if (!ok) return false;
        return nextHandler.map(courseEnrolmentHandler -> courseEnrolmentHandler.handleEnrolment(student, course)).orElse(true);
    }
}
