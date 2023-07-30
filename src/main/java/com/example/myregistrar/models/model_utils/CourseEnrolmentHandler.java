package com.example.myregistrar.models.model_utils;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

public interface CourseEnrolmentHandler {
    void setNextHandler(CourseEnrolmentHandler nextHandler);
    boolean handleEnrolment(Student student, Course course);
}
