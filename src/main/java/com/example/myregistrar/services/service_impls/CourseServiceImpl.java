package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.CoursePreRequisite;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;
    private final CoursePreRequiteRepo coursePreRequiteRepo;
    private final StudentRepo studentRepo;
    private final BookRepo bookRepo;

    @Transactional
    @Override
    public void createCourse(Course course) {
        if (course == null) {
            log.error("The course is null");
            return;
        }
        if (courseRepo.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new CourseAlreadyExistsException("Course with such name and university already exists");
        }
        courseRepo.save(course);
    }

    @Override
    public void generateRandomCourses(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createCourse(CourseDto.createRandomCourseDto().toCourse());
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
        if (student == null) {
            log.error("The student is null");
            throw new StudentNotFoundException("The student is null");
        }
        List<Course> courses = courseRepo.findCoursesByStudentId(student.getId());
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("The student does not have any course");
        }
        return courses;
    }

    @Transactional
    @Override
    public void assignBooksToCourse(Course course, List<Book> books) {
        if (course == null || books == null) {
            log.error("The course is null or book list is null");
            return;
        }

        List<Book> bookListByCourseId = bookRepo.findBooksByCourseId(course.getId());
        bookListByCourseId.addAll(books);
        course.setBooks(bookListByCourseId);

        books.forEach(book -> book.setCourse(course));
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void assignStudentsToCourse(Course course, List<Student> students) {
        if (course == null || students == null) {
            log.error("The course is null or student list is null");
            return;
        }

        List<Student> studentListByCourse = studentRepo.findStudentsByCourseId(course.getId());
        studentListByCourse.addAll(students);

        course.setStudents(studentListByCourse);

        students.forEach(student -> {
            List<Course> courseListByStudent = courseRepo.findCoursesByStudentId(student.getId());
            courseListByStudent.add(course);

            student.setCourses(courseListByStudent);
        });
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void removeCoursePreRequisiteFromCourse(Course course, Course coursePreReq) {
        if (course == null || coursePreReq == null || Objects.equals(course.getId(), coursePreReq.getId())) {
            log.error("Method Not Allowed");
            return;
        }

        coursePreRequiteRepo.deleteByCourseIdAndCoursePreReqId(course.getId(), coursePreReq.getId());
    }

    @Transactional
    @Override
    public void assignCoursePreRequisiteCourse(Course course, Course coursePreReq) {
        if (course == null || coursePreReq == null || Objects.equals(course.getId(), coursePreReq.getId())) {
            log.error("Method Not Allowed");
            return;
        }

        CoursePreRequisiteId coursePreRequisiteId = new CoursePreRequisiteId(course.getId(), coursePreReq.getId());
        CoursePreRequisite coursePreRequisite = new CoursePreRequisite(coursePreRequisiteId, course, coursePreReq);

        coursePreRequiteRepo.save(coursePreRequisite);
    }

    @Transactional
    @Override
    public List<Course> getCoursePreRequisitesFromCourse(Course course) {
        if (course == null) throw new CourseNotFoundException("The course is null");
        return coursePreRequiteRepo.findPrerequisiteCoursesByCourseId(course.getId());
    }
}
