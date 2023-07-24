package com.example.myregistrar.services;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

import java.util.List;

public interface StudentService {
    void createStudent(Student student);

    void generateRandomStudents(int n);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    List<Student> getStudentsByFirstName(String firstName);

    List<Student> getStudentsByLastName(String lastName);

    Student getStudentByFirstNameAndLastName(String firstName, String lastName);

    List<Student> getStudentsByCourse(Course course);

    void assignCoursesToStudent(Student student, List<Course> courses);

    void assignCourseToStudent(Student student, Long courseId);
}
