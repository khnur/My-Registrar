package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentAlreadyExistsException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    @Transactional
    @Override
    public void createStudent(Student student) {
        if (studentRepo.existsStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName())) {
            throw new StudentAlreadyExistsException("Student with such name and last name already exists");
        }
        studentRepo.save(student);
    }

    @Override
    public void generateRandomStudents(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createStudent(StudentDto.createRandomStudentDto().toStudent());
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
        return studentRepo.findStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=\" + id + \" does not exists"));
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
        if (course == null) {
            throw new StudentNotFoundException("The course is null");
        }
        List<Student> students = studentRepo.findStudentsByCourseId(course.getId());
        if (students.isEmpty()) {
            throw new StudentNotFoundException("The course does not have any student");
        }
        return students;
    }

    @Transactional
    @Override
    public void assignCoursesToStudent(Student student, List<Course> courses) {
        if (student == null || courses == null) {
            throw new NoSuchElementException("The course is null or student list is null");
        }

        List<Course> courseListByStudent = courseRepo.findCoursesByStudentId(student.getId());
        courseListByStudent.addAll(courses);

        student.setCourses(courseListByStudent);

        courses.forEach(course -> {
            List<Student> studentListByStudent = studentRepo.findStudentsByCourseId(course.getId());
            studentListByStudent.add(student);

            course.setStudents(studentListByStudent);
            courseRepo.save(course);
        });
        studentRepo.save(student);
    }

    @Transactional
    @Override
    public void assignCourseToStudent(Student student, Long courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("There is not course with id=" + courseId));

        List<Course> courseListByStudent = courseRepo.findCoursesByStudentId(student.getId());
        courseListByStudent.add(course);

        List<Student> studentListByStudent = studentRepo.findStudentsByCourseId(courseId);
        studentListByStudent.add(student);

        courseRepo.save(course);
        studentRepo.save(student);
    }
}
