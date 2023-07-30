package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.*;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    BookService bookService;
    @Mock
    UniversityService universityService;
    @InjectMocks
    StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        when(studentService.createStudent(any())).thenReturn(student);

        StudentDto result = studentController.createStudent(new StudentDto());
        Assertions.assertEquals(student.toStudentDto(), result);
    }

    @Test
    void testCreateStudent_InvalidData() {
        when(studentService.createStudent(any())).thenThrow(new StudentNotFoundException("Invalid student data"));
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentController.createStudent(new StudentDto()));
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = List.of(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        when(studentService.getAllStudents()).thenReturn(students);

        List<StudentDto> result = studentController.getAllStudents();
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetStudentById() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        when(studentService.getStudentById(anyLong())).thenReturn(student);

        StudentDto result = studentController.getStudentById(Long.valueOf(1));
        Assertions.assertEquals(student.toStudentDto(), result);
    }

    @Test
    void testGetStudentReportById() {
        when(studentService.getStudentById(anyLong())).thenReturn(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        when(studentService.getStudentReport(any())).thenReturn(new StudentReportDto("name", "email", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), List.of(new CourseDto()), new UniversityDto(Long.valueOf(1), "name", "country", "city")));

        StudentReportDto result = studentController.getStudentReportById(Long.valueOf(1));
        Assertions.assertEquals(new StudentReportDto("name", "email", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), List.of(new CourseDto()), new UniversityDto(Long.valueOf(1), "name", "country", "city")), result);
    }

    @Test
    void testGetUniversityByStudentId() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        student.setId(1L);
        when(studentService.getStudentById(anyLong())).thenReturn(student);

        Assertions.assertThrows(UniversityNotFoundException.class,
                () -> studentController.getUniversityByStudentId(1L));
    }

    @Test
    void testAssignStudentToUniversity() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");

        when(studentService.getStudentById(anyLong())).thenReturn(student);
        when(universityService.getUniversityById(anyLong())).thenReturn(new University("name", "country", "city"));
        when(universityService.getUniversityByNameAndCountry(anyString(), anyString())).thenReturn(new University("name", "country", "city"));

        StudentDto result = studentController.assignStudentToUniversity(Long.valueOf(1), new UniversityDto(Long.valueOf(1), "name", "country", "city"));
        Assertions.assertEquals(student.toStudentDto(), result);
    }

    @Test
    void testGetCourseByStudentId() {
        when(studentService.getStudentById(anyLong())).thenReturn(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getCoursesByStudent(any())).thenReturn(courseList);

        List<CourseDto> result = studentController.getCourseByStudentId(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testGetBooksByStudentId() {
        when(studentService.getStudentById(anyLong())).thenReturn(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        List<Book> bookList = List.of(new Book("name", "author", "genre",
                LocalDate.EPOCH, "publisher", 0));
        when(bookService.getBooksByStudent(any())).thenReturn(
                bookList);

        List<BookDto> result = studentController.getBooksByStudentId(1L);
        Assertions.assertEquals(BookMapper.INSTANCE.bookListToBookDtoList(bookList), result);
    }
}