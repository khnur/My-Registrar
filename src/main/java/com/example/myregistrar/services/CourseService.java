package com.example.myregistrar.services;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);

    void generateRandomCourses(int n);

    Course getCourseById(Long id);

    List<Course> getAllCourses();

    List<Course> getCoursesByName(String name);

    Course getCoursesByNameAndDepartment(String name, String department);

    List<Course> getCoursesByUniversity(University university);

    Course getCourseByNameAndUniversityId(String name, Long universityId);

    List<Course> getCoursesByStudent(Student student);

    void assignBookToCourse(Course course, Book book);

    void assignStudentToCourse(Course course, Student student);

    void removeCoursePreRequisiteFromCourse(Course course, Course coursePreReq);

    void assignCoursePreRequisiteCourse(Course course, Course coursePreReq);

    List<Course> getCoursePreRequisitesFromCourse(Course course);
    void assignUniversityToCourse(Course course, University university);
}
