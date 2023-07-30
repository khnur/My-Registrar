package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.StudentReportDto;
import com.example.myregistrar.exceptions.*;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.models.model_utils.StudentEnrolmentManager;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.NewModel;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
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
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final CoursePreRequiteRepo coursePreRequiteRepo;
    private final BookRepo bookRepo;

    private final StudentEnrolmentManager studentEnrolmentManager;

    @Transactional
    @Override
    public Student createStudent(Student student) {
        if (student == null) {
            throw new StudentNotFoundException("Provided student null");
        } else if (student.getId() != null ||
                studentRepo.existsStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName())) {
            throw new StudentAlreadyExistsException("Student with such name and last name already exists");
        }
        return studentRepo.save(student);
    }

    @Override
    public void generateRandomStudents(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createStudent(NewModel.createRandomStudent());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=" + id + " does not exists"));
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = studentRepo.findAll();
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student");
        }
        return studentList;
    }

    @Override
    public List<Student> getStudentsByFirstName(String firstName) {
        List<Student> studentList = studentRepo.findStudentsByFirstName(firstName);
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such first name");
        }
        return studentList;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> studentList = studentRepo.findStudentsByLastName(lastName);
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such last name");
        }
        return studentList;
    }

    @Override
    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepo.findStudentByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new StudentNotFoundException("There is no student with such first and last name"));
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        if (course == null || course.getId() == null) {
            throw new CourseNotFoundException("The course is null or has not been registered");
        }
        List<Student> students = studentRepo.findStudentsByCourseId(course.getId());
        if (students.isEmpty()) {
            throw new StudentNotFoundException("The course does not have any student");
        }
        return students;
    }

    @Override
    public List<Student> getStudentsByUniversity(University university) {
        if (university == null || university.getId() == null) {
            throw new UniversityNotFoundException("provided university is null or has not been registered");
        }
        List<Student> students = studentRepo.findStudentsByUniversityId(university.getId());
        if (students.isEmpty()) {
            throw new StudentNotFoundException("There is no student at the university with name=" + university.getName());
        }
        return students;
    }

    @Transactional
    @Override
    public void assignCourseToStudent(Student student, Course course) {
        if (student == null || course == null) {
            throw new NoSuchElementException("The course is null or student is null");
        } else if (student.getId() == null) {
            throw new StudentNotFoundException("Provided transient student, should be registered");
        } else if (course.getId() == null) {
            throw new CourseNotFoundException("Provided transient course, should be registered");
        }

        if (student.getUniversity() == null) {
            throw new UniversityNotFoundException("Student with id=" + student.getId() +
                    " can not enrol any course. Student has not applied to any university");
        } else if (course.getUniversity() == null) {
            throw new UniversityNotFoundException("Course with id=" + course.getId() +
                    " is not registered by any university");
        } else if (!Objects.equals(student.getUniversity().getId(), course.getUniversity().getId())) {
            throw new RuntimeException("Student with id=" + student.getId() + " can not take course with id=" + course.getId());
        }

        if (studentEnrolmentManager.studentPasses(student, course)) {
            throw new RuntimeException("Student with id=" + student.getId() + " is not eligible to take course with id=" + course.getId());
        }

        List<Course> courseListByStudent = courseRepo.findCoursesByStudentId(student.getId());
        courseListByStudent.add(course);

        student.setCourses(courseListByStudent);

        List<Student> studentListByStudent = studentRepo.findStudentsByCourseId(course.getId());
        studentListByStudent.add(student);
        student.updateCourse();

        course.setStudents(studentListByStudent);

        courseRepo.save(course);
        studentRepo.save(student);
    }

    @Transactional
    @Override
    public void assignUniversityToStudent(Student student, University university) {
        if (student == null || university == null || student.getId() == null || university.getId() == null) {
            throw new NoSuchElementException("provided student or university is null or has not been registered");
        } else if (student.getUniversity() != null && student.getUniversity().getId() != null) {
            throw new UniversityAlreadyExistsException("student with id=" + student.getId() +
                    " has already been assigned to university");
        }

        student.setUniversity(university);
        studentRepo.save(student);
    }

    @Override
    public StudentReportDto getStudentReport(Student student) {
        if (student == null || student.getId() == null) {
            throw new StudentNotFoundException("provided student is null or has not been registered");
        }
        StudentReportDto studentReport = new StudentReportDto();

        studentReport.setName(student.getFirstName() + " " + student.getLastName());
        studentReport.setEmail(student.getEmail());

        if (student.getUniversity() == null) {
            return studentReport;
        }

        studentReport.setUniversity(
                UniversityMapper.INSTANCE.universityToUniversityDto(student.getUniversity())
        );

        List<Course> courses = courseRepo.findCoursesByStudentId(student.getId());

        studentReport.setCourses(
                CourseMapper.INSTANCE.courseListToCourseDtoList(courses)
        );

        final int[] properties = new int[5];
        courses.stream()
                .flatMap(course -> {
                    if (course == null || course.getId() == null) {
                        throw new CourseNotFoundException("course is null or has not been registered");
                    }
                    properties[1]++;
                    properties[2] += course.getCreditHours();

                    bookRepo.findBooksByCourseId(course.getId())
                            .forEach(book -> properties[4] += book.getPageNumber());

                    return coursePreRequiteRepo.findPrerequisiteCoursesByCourseId(course.getId()).stream();
                })
                .distinct()
                .forEach(coursePreRequite -> {
                    if (coursePreRequite == null || coursePreRequite.getId() == null) {
                        throw new CourseNotFoundException("coursePreReq is null or has not been registered");
                    }
                    properties[0]++;
                    properties[2] += coursePreRequite.getCreditHours();

                    bookRepo.findBooksByCourseId(coursePreRequite.getId())
                            .forEach(book -> properties[3] += book.getPageNumber());
                });

        studentReport.setNumberOfCoursesCompleted(properties[0]);
        studentReport.setNumberOfCoursesTaking(properties[1]);
        studentReport.setTotalCreditHours(properties[2]);
        studentReport.setPageNumberRead(properties[3]);
        studentReport.setPageNumberUpToRead(properties[4]);

        return studentReport;
    }
}
