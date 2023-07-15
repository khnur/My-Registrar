package com.example.practice_1.services;

import com.example.practice_1.exceptions.CourseAlreadyExistsException;
import com.example.practice_1.exceptions.CourseNotFoundException;
import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.tables.BookTable;
import com.example.practice_1.tables.CourseTable;
import com.example.practice_1.tables.StudentTable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CourseService extends AbstractService {
    public CourseService(StudentTable studentData, CourseTable courseData, BookTable bookData) {
        super(studentData, courseData, bookData);
    }

    public void createCourse(Course course) {
        if (courseData.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new CourseAlreadyExistsException("Course with such name and university already exists");
        }
        courseData.save(course);
    }

    public void createRandomCourses(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createCourse(Course.createRandomCourse());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = courseData.findAll();
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course");
        }
        return courseList;
    }

    public List<Course> getCoursesByName(String name) {
        List<Course> courseList = courseData.findCoursesByName(name);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such name");
        }
        return courseList;
    }

    public List<Course> getCoursesByUniversity(String university) {
        List<Course> courseList = courseData.findCoursesByUniversity(university);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such university");
        }
        return courseList;
    }

    public Course getCourseByNameAndUniversity(String name, String university) {
        return courseData.findCourseByNameAndUniversity(name, university)
                .orElseThrow(() -> new RuntimeException("There is no course with such name and university"));
    }

    public void assignBooksToCourse(Course course, List<Book> books) {
        course.setBooks(books);
        for (Book book : books) {
            book.setCourse(course);
        }
        courseData.save(course);
    }
}
