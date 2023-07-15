package com.example.practice_1.services;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.repos.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public Course createCourse(Course course) throws RuntimeException {
        if (courseRepo.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new RuntimeException("Course with such name and university already exists");
        }
        return courseRepo.save(course);
    }

    public void createRandomCourses(int n) {
        for (int i = 0; i < n; ) {
            try {
                createCourse(Course.createRandomCourse());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public List<Course> getAllCourses() throws RuntimeException {
        List<Course> courseList = courseRepo.findAll();
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course");
        }
        return courseList;
    }

    public List<Course> getCoursesByName(String name) throws RuntimeException {
        List<Course> courseList = courseRepo.findCoursesByName(name);
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course with such name");
        }
        return courseList;
    }

    public List<Course> getCoursesByUniversity(String university) throws RuntimeException {
        List<Course> courseList = courseRepo.findCoursesByUniversity(university);
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course with such university");
        }
        return courseList;
    }

    public Course getCourseByNameAndUniversity(String name, String university) throws RuntimeException {
        return courseRepo.findCourseByNameAndUniversity(name, university)
                .orElseThrow(() -> new RuntimeException("There is no course with such name and university"));
    }

    public void assignBooksToCourse(Course course, List<Book> books) throws RuntimeException {
        course.setBooks(books);
        for (Book book : books) {
            book.setCourse(course);
        }
        courseRepo.save(course);
    }
}
