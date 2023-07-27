package com.example.myregistrar.services;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    void generateRandomStudents(int n);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    List<Student> getStudentsByFirstName(String firstName);

    List<Student> getStudentsByLastName(String lastName);

    Student getStudentByFirstNameAndLastName(String firstName, String lastName);

    List<Student> getStudentsByCourse(Course course);

    List<Student> getStudentsByUniversity(University university);

    void assignCourseToStudent(Student student, Course course);

    void assignCourseToStudent(Student student, Long courseId);

    void assignUniversityToStudent(Student student, University university);
}
