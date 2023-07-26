package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.exceptions.*;
import com.example.myregistrar.jms.KafkaService;
import com.example.myregistrar.models.*;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.util.NewModel;
import jakarta.el.MethodNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;
    private final CoursePreRequiteRepo coursePreRequiteRepo;
    private final StudentRepo studentRepo;
    private final BookRepo bookRepo;

    private final KafkaService kafkaService;

    @Transactional
    @Override
    public void createCourse(Course course) {
        if (course == null || course.getId() != null) {
            throw new CourseAlreadyExistsException("Course with such id already exists");
        }
        Course newCourse = courseRepo.save(course);
        kafkaService.sendToCourseTopic(newCourse.toCourseDto().toJson());
    }

    @Override
    public void generateRandomCourses(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createCourse(NewModel.createRandomCourse());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with id=" + id + " does not exists"));
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
    public Course getCoursesByNameAndDepartment(String name, String department) {
        return courseRepo.findCourseByNameAndDepartment(name, department)
                .orElseThrow(() -> new CourseNotFoundException("There is no course with name=" + name +
                        " and deparment=" + department));
    }

    @Override
    public List<Course> getCoursesByUniversityId(Long universityId) {
        List<Course> courseList = courseRepo.findCoursesByUniversityId(universityId);
        if (courseList.isEmpty()) {
            throw new CourseNotFoundException("There is no course with such university id");
        }
        return courseList;
    }

    @Override
    public Course getCourseByNameAndUniversityId(String name, Long universityId) {
        return courseRepo.findCourseByNameAndUniversityId(name, universityId)
                .orElseThrow(() -> new CourseNotFoundException("There is no course with such name and university"));
    }

    @Override
    public List<Course> getCoursesByStudent(Student student) {
        if (student == null) {
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
            throw new NoSuchElementException("The course is null or book list is null");
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
            throw new NoSuchElementException("The course is null or student list is null");
        }

        List<Student> studentListByCourse = studentRepo.findStudentsByCourseId(course.getId());
        studentListByCourse.addAll(students);

        course.setStudents(studentListByCourse);

        students.forEach(student -> {
            List<Course> courseListByStudent = courseRepo.findCoursesByStudentId(student.getId());
            courseListByStudent.add(course);

            student.setCourses(courseListByStudent);
            studentRepo.save(student);
        });
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void removeCoursePreRequisiteFromCourse(Course course, Course coursePreReq) {
        if (course == null || coursePreReq == null) {
            throw new NoSuchElementException("The course is null or course pre-requisite is null");
        } else if (Objects.equals(course.getId(), coursePreReq.getId())) {
            throw new MethodNotFoundException("Method Not Allowed");
        }

        coursePreRequiteRepo.deleteByCourseIdAndCoursePreReqId(course.getId(), coursePreReq.getId());
    }

    @Transactional
    @Override
    public void assignCoursePreRequisiteCourse(Course course, Course coursePreReq) {
        if (course == null || coursePreReq == null) {
            throw new NoSuchElementException("The course is null or course pre-requisite is null");
        } else if (Objects.equals(course.getId(), coursePreReq.getId())) {
            throw new MethodNotFoundException("Method Not Allowed");
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

    @Transactional
    @Override
    public void assignUniversityToCourse(Course course, University university) {
        if (course == null || university == null) {
            throw new NoSuchElementException("provided course or university is null");
        }
        if (course.getUniversity() != null && course.getUniversity().getId() != null) {
            throw new UniversityAlreadyExists("course with id=" + course.getId() + " has already been assigned to university");
        }
        if (university.getId() == null) {
            throw new UniversityNotFound("university is not registered");
        }

        course.setUniversity(university);
        courseRepo.save(course);
    }
}
