package com.example.myregistrar.services;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

import java.util.List;

public interface CourseService {
    void createCourse(Course course);

    void createCourse(CourseDto courseDto);

    void createRandomCourses(int n);

    List<Course> getAllCourses();

    List<Course> getCoursesByName(String name);

    List<Course> getCoursesByUniversity(String university);

    Course getCourseByNameAndUniversity(String name, String university);

    List<Course> getCoursesByStudent(Student student);

    void assignBooksToCourse(Course course, List<Book> books);

    void assignStudentsToCourse(Course course, List<Student> students);

    void removeCoursePreRequisiteFromCourse(Course course, Course coursePreReq);

    void assignCoursePreRequisiteCourse(Course course, Course coursePreReq);

    List<Course> getCoursePreRequisitesFromCourse(Course course);
}
