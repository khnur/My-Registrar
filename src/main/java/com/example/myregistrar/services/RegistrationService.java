package com.example.myregistrar.services;

import com.example.myregistrar.embeddables.RegistrationId;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Registration;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repos.RegistrationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepo registrationRepo;

    public List<Course> getCoursesByStudent(Student student) {
        List<Course> courseList = registrationRepo.findCoursesByStudent(student);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("The student does not have any registered course");
        }
        return courseList;
    }

    public List<Student> getStudentsByCourse(Course course) {
        List<Student> studentsByCourse = registrationRepo.findStudentsByCourse(course);
        if (studentsByCourse.isEmpty()) {
            throw new StudentNotFoundException("There is no student in this course");
        }
        return studentsByCourse;
    }

    public void assignCoursesToStudent(Student student, List<Course> courses) {
        LocalDateTime registeredTime = LocalDateTime.now();

        for (Course course : courses) {
            RegistrationId registrationId = new RegistrationId(student.getId(), course.getId());
            Registration registration = new Registration(registrationId, student, course, registeredTime);
            registrationRepo.save(registration);
        }
    }
}
