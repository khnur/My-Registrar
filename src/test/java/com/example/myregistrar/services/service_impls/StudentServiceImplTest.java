package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.StudentReportDto;
import com.example.myregistrar.exceptions.StudentAlreadyExistsException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.exceptions.UniversityAlreadyExistsException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {
    @Mock
    StudentRepo studentRepo;
    @Mock
    CourseRepo courseRepo;
    @Mock
    CoursePreRequiteRepo coursePreRequiteRepo;
    @Mock
    BookRepo bookRepo;
    @InjectMocks
    StudentServiceImpl studentServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent_StudentDoesNotExist() throws ParseException {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(false);

        Student mockStudent = new Student(
                "aaa",
                "aaa",
                LocalDate.EPOCH,
                "M"
        );

        when(studentRepo.save(any(Student.class))).thenReturn(mockStudent);

        Student result = studentServiceImpl.createStudent(mockStudent);

        assertEquals(mockStudent, result);
    }

    @Test
    void testCreateStudent_StudentAlreadyExists() {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);
        assertThrows(StudentAlreadyExistsException.class, () -> {
            Student student = new Student(
                    "aaa",
                    "aaa",
                    LocalDate.EPOCH,
                    "M"
            );
            studentServiceImpl.createStudent(student);
        });
    }

    @Test
    void testCreateStudent_NullStudent() {
        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.createStudent(null));
    }

    @Test
    void testGenerateRandomStudents_StudentsExist() {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(false);

        int numberOfRandomStudentsToGenerate = 5;
        studentServiceImpl.generateRandomStudents(numberOfRandomStudentsToGenerate);

        verify(studentRepo, times(numberOfRandomStudentsToGenerate)).existsStudentByFirstNameAndLastName(anyString(), anyString());
        verify(studentRepo, times(numberOfRandomStudentsToGenerate)).save(any(Student.class));
    }

    @Test
    void testGetStudentById_StudentExists() {
        long studentId = 1L;
        Student mockStudent = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        when(studentRepo.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Student result = studentServiceImpl.getStudentById(studentId);

        assertEquals(mockStudent, result);
    }

    @Test
    void testGetStudentById_StudentDoesNotExist() {
        when(studentRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.getStudentById(1L));
    }

    @Test
    void testGetAllStudents() {
        List<Student> mockStudentList = List.of(
                new Student("firstName1", "lastName1", LocalDate.EPOCH, "gender1"),
                new Student("firstName2", "lastName2", LocalDate.EPOCH, "gender2"),
                new Student("firstName3", "lastName3", LocalDate.EPOCH, "gender3")
        );
        when(studentRepo.findAll()).thenReturn(mockStudentList);

        List<Student> result = studentServiceImpl.getAllStudents();

        assertEquals(mockStudentList, result);
    }

    @Test
    void testGetStudentsByFirstName() {
        List<Student> mockStudentList = List.of(
                new Student("firstName1", "lastName1", LocalDate.EPOCH, "gender1"),
                new Student("firstName2", "lastName2", LocalDate.EPOCH, "gender2"),
                new Student("firstName3", "lastName3", LocalDate.EPOCH, "gender3")
        );
        when(studentRepo.findStudentsByFirstName("firstName")).thenReturn(mockStudentList);

        List<Student> result = studentServiceImpl.getStudentsByFirstName("firstName");

        assertEquals(mockStudentList, result);
    }

    @Test
    void testGetStudentsByLastName() {
        List<Student> mockStudentList = List.of(
                new Student("firstName1", "lastName1", LocalDate.EPOCH, "gender1"),
                new Student("firstName2", "lastName2", LocalDate.EPOCH, "gender2"),
                new Student("firstName3", "lastName3", LocalDate.EPOCH, "gender3")
        );
        when(studentRepo.findStudentsByLastName("lastName")).thenReturn(mockStudentList);

        List<Student> result = studentServiceImpl.getStudentsByLastName("lastName");

        assertEquals(mockStudentList, result);
    }

    @Test
    void testGetStudentByFirstNameAndLastName_StudentExists() {
        Student mockStudent = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        when(studentRepo.findStudentByFirstNameAndLastName("firstName", "lastName")).thenReturn(Optional.of(mockStudent));

        Student result = studentServiceImpl.getStudentByFirstNameAndLastName("firstName", "lastName");

        assertEquals(mockStudent, result);
    }

    @Test
    void testGetStudentByFirstNameAndLastName_StudentDoesNotExist() {
        when(studentRepo.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.getStudentByFirstNameAndLastName("firstName", "lastName"));
    }

    @Test
    void testGetStudentsByCourse() {
        List<Student> mockStudentList = List.of(
                new Student("firstName1", "lastName1", LocalDate.EPOCH, "gender1"),
                new Student("firstName2", "lastName2", LocalDate.EPOCH, "gender2"),
                new Student("firstName3", "lastName3", LocalDate.EPOCH, "gender3")
        );
        when(studentRepo.findStudentsByCourseId(anyLong())).thenReturn(mockStudentList);

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(1L);
        when(courseRepo.findById(anyLong())).thenReturn(Optional.of(course));

        List<Student> result = studentServiceImpl.getStudentsByCourse(course);

        assertEquals(mockStudentList, result);
    }

    @Test
    void testGetStudentsByUniversity() {
        List<Student> mockStudentList = List.of(
                new Student("firstName1", "lastName1", LocalDate.EPOCH, "gender1"),
                new Student("firstName2", "lastName2", LocalDate.EPOCH, "gender2"),
                new Student("firstName3", "lastName3", LocalDate.EPOCH, "gender3")
        );
        when(studentRepo.findStudentsByUniversityId(anyLong())).thenReturn(mockStudentList);

        University university = new University("name", "country", "city");
        university.setId(1L);

        List<Student> result = studentServiceImpl.getStudentsByUniversity(university);

        assertEquals(mockStudentList, result);
    }

    @Test
    void testAssignCourseToStudent_StudentNotRegistered() {
        Course mockCourse = new Course("name", "department", "instructor", 1);
        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.assignCourseToStudent(new Student("firstName", "lastName", LocalDate.EPOCH, "gender"), mockCourse));
    }

    @Test
    void testAssignCourseToStudent_DifferentUniversities() {
        University university1 = new University("university1", "country1", "city1");
        university1.setId(1L);
        University university2 = new University("university2", "country2", "city2");
        university2.setId(2L);

        Course mockCourse = new Course("name", "department", "instructor", 1);
        mockCourse.setUniversity(university1);
        Student mockStudent = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        mockStudent.setId(1L);
        mockStudent.setUniversity(university2);

        assertThrows(RuntimeException.class, () -> studentServiceImpl.assignCourseToStudent(mockStudent, mockCourse));
    }

    @Test
    void testAssignUniversityToStudent_StudentAlreadyHasUniversity() {
        University mockUniversity = new University("name", "country", "city");
        mockUniversity.setId(1L);

        Student mockStudent = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        mockStudent.setId(1L);

        mockStudent.setUniversity(mockUniversity);

        assertThrows(UniversityAlreadyExistsException.class,
                () -> studentServiceImpl.assignUniversityToStudent(mockStudent, mockUniversity));
    }

    @Test
    void testGetStudentReport() {
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));

        when(courseRepo.findCoursesByStudentId(anyLong())).thenReturn(courseList);
        when(coursePreRequiteRepo.findPrerequisiteCoursesByCourseId(anyLong())).thenReturn(courseList);
        when(bookRepo.findBooksByCourseId(anyLong())).thenReturn(List.of(new Book(null, null, null, null, null, 0)));

        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        student.setId(1L);

        StudentReportDto result = studentServiceImpl.getStudentReport(student);

        StudentReportDto expected = new StudentReportDto("firstName lastName", "firstname.lastname@onelab.kz", null, null, null, null, null, null, null);
        assertEquals(expected, result);
    }

    @Test
    void testGetStudentReport_NoUniversityAssociated() {
        Student mockStudent = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        mockStudent.setId(1L);

        String email = mockStudent.getFirstName().toLowerCase().trim() + "." + mockStudent.getLastName().toLowerCase().trim()
                + "@onelab.kz";

        StudentReportDto result = studentServiceImpl.getStudentReport(mockStudent);

        assertEquals("firstName lastName", result.getName());
        assertEquals(email, result.getEmail());
        assertNull(result.getUniversity());
    }
}