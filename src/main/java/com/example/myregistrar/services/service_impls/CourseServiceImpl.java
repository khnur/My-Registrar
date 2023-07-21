package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;

    @Transactional
    @Override
    public void createCourse(Course course) {
        if (courseRepo.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new CourseAlreadyExistsException("Course with such name and university already exists");
        }
        courseRepo.save(course);
    }

    @Override
    public void createCourse(CourseDto courseDto) {
        Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);
        createCourse(course);
    }

    @Override
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

    @Override
    public List<Course> getAllCourses() {
        List<Course> courseList = courseRepo.findAll();
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course");
        }
        return courseList;
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        List<Course> courseList = courseRepo.findCoursesByName(name);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such name");
        }
        return courseList;
    }

    @Override
    public List<Course> getCoursesByUniversity(String university) {
        List<Course> courseList = courseRepo.findCoursesByUniversity(university);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such university");
        }
        return courseList;
    }

    @Override
    public Course getCourseByNameAndUniversity(String name, String university) {
        return courseRepo.findCourseByNameAndUniversity(name, university)
                .orElseThrow(() -> new RuntimeException("There is no course with such name and university"));
    }

    @Override
    public List<Course> getCoursesByStudent(Student student) {
        List<Course> courses = student.getCourses();
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("The student does not have any course");
        }
        return courses;
    }

    @Transactional
    @Override
    public void assignBooksToCourse(Course course, List<Book> books) {
        course.getBooks().addAll(books);
        books.forEach(book -> book.setCourse(course));
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void assignStudentsToCourse(Course course, List<Student> students) {
        course.setStudents(students);
        students.forEach(student -> student.getCourses().add(course));
        courseRepo.save(course);
    }
}
