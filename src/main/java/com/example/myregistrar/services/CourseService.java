package com.example.myregistrar.services;

import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public void createCourse(Course course) {
        if (courseRepo.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new CourseAlreadyExistsException("Course with such name and university already exists");
        }
        courseRepo.save(course);
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
        List<Course> courseList = courseRepo.findAll();
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course");
        }
        return courseList;
    }

    public List<Course> getCoursesByName(String name) {
        List<Course> courseList = courseRepo.findCoursesByName(name.trim());
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such name");
        }
        return courseList;
    }

    public List<Course> getCoursesByUniversity(String university) {
        List<Course> courseList = courseRepo.findCoursesByUniversity(university.trim());
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such university");
        }
        return courseList;
    }

    public Course getCourseByNameAndUniversity(String name, String university) {
        return courseRepo.findCourseByNameAndUniversity(name.trim(), university.trim())
                .orElseThrow(() -> new RuntimeException("There is no course with such name and university"));
    }

    public List<Course> getCoursesByStudent(Student student) {
        List<Course> courses = student.getCourses().stream().toList();
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("The student does not have any course");
        }
        return courses;
    }

    public void assignBooksToCourse(Course course, List<Book> books) {
        course.getBooks().addAll(books);
        books.forEach(book -> book.setCourse(course));
        courseRepo.save(course);
    }
}
