package com.example.practice_1.services;

import com.example.practice_1.embeddables.RegistrationId;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Registration;
import com.example.practice_1.models.Student;
import com.example.practice_1.repos.RegistrationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepo registrationRepo;

    public List<Course> getCoursesByStudent(Student student) throws RuntimeException {
        List<Course> courseList = registrationRepo.findCoursesByStudent(student);
        if (courseList.isEmpty()) {
            throw new RuntimeException("The student does not have any registered course");
        }
        return courseList;
    }

    public List<Student> getStudentsByCourse(Course course) {
        List<Student> studentsByCourse = registrationRepo.findStudentsByCourse(course);
        if (studentsByCourse.isEmpty()) {
            throw new RuntimeException("There is no student in this course");
        }
        return studentsByCourse;
    }

    public void assignCoursesToStudent(Student student, List<Course> courses) throws RuntimeException {
        LocalDateTime registeredTime = LocalDateTime.now();

        for (Course course : courses) {
            RegistrationId registrationId = new RegistrationId(student.getId(), course.getId());
            Registration registration = new Registration(registrationId, student, course, registeredTime);
            registrationRepo.save(registration);
        }
    }
}
