package com.example.myregistrar.models.model_utils;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class EligibilityCheckHandler implements CourseEnrolmentHandler {
    private Optional<CourseEnrolmentHandler> nextHandler = Optional.empty();

    @Override
    public void setNextHandler(CourseEnrolmentHandler nextHandler) {
        this.nextHandler = Optional.of(nextHandler);
    }

    @Override
    public boolean handleEnrolment(Student student, Course course) {
        if (student == null || student.getId() == null ||
                student.getUniversity() == null || student.getUniversity().getId() == null) {
            return false;
        }
        boolean ok = (student.getAge() != null && student.getAge() >= 18) ||
                (student.getBirthDate() != null && Period.between(student.getBirthDate(), LocalDate.now()).getYears() >= 18);

        if (!ok) return false;
        return nextHandler.map(courseEnrolmentHandler -> courseEnrolmentHandler.handleEnrolment(student, course)).orElse(true);
    }
}
