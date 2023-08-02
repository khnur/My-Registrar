package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.*;
import com.example.myregistrar.exceptions.not_found.StudentNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    StudentFacade studentFacade;
    @Mock
    CourseFacade courseService;
    @Mock
    BookFacade bookService;
    @Mock
    UniversityFacade universityService;
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
        when(studentFacade.createStudent(any())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));

        StudentDto result = studentController.createStudent(new StudentDto()).getObject();
        Assertions.assertEquals(StudentMapper.INSTANCE.studentToStudentDto(student), result);
    }

    @Test
    void testCreateStudent_InvalidData() {
        when(studentFacade.createStudent(any())).thenThrow(new StudentNotFoundException("Invalid student data"));
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentController.createStudent(new StudentDto()));
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = List.of(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        when(studentFacade.getAllStudents()).thenReturn(StudentMapper.INSTANCE.studentListToStudentDtoList(students));

        List<StudentDto> result = studentController.getAllStudents();
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetStudentById() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));

        StudentDto result = studentController.getStudentById(Long.valueOf(1));
        Assertions.assertEquals(StudentMapper.INSTANCE.studentToStudentDto(student), result);
    }

    @Test
    void testGetStudentReportById() {
        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));
        when(studentFacade.getStudentReportById(any())).thenReturn(new StudentReportDto("name", "email", 0, 0, 0, 0, 0, List.of(new CourseDto()), new UniversityDto(Long.valueOf(1), "name", "country", "city")));

        StudentReportDto result = studentController.getStudentReportById(1L);
        Assertions.assertEquals(new StudentReportDto("name", "email", 0, 0, 0, 0, 0, List.of(new CourseDto()), new UniversityDto(Long.valueOf(1), "name", "country", "city")), result);
    }

    @Test
    void testGetUniversityByStudentId() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");
        student.setId(1L);
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));

        Assertions.assertDoesNotThrow(() -> studentController.getUniversityByStudentId(1L));
    }

    @Test
    void testAssignStudentToUniversity() {
        Student student = new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender");

        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));
        University university = new University("name", "country", "city");
        when(universityService.getUniversityById(anyLong())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        StudentDto result = studentController.assignStudentToUniversity(1L, new UniversityDto(1L, "name", "country", "city"));
        Assertions.assertNotEquals(StudentMapper.INSTANCE.studentToStudentDto(student), result);
    }

    @Test
    void testGetCourseByStudentId() {
        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getCourseByStudentId(any())).thenReturn(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList));

        List<CourseDto> result = studentController.getCourseByStudentId(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testGetBooksByStudentId() {
        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(student));
        List<Book> bookList = List.of(new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0));
        when(bookService.getBooksByStudentId(any())).thenReturn(BookMapper.INSTANCE.bookListToBookDtoList(bookList));

        List<BookDto> result = studentController.getBooksByStudentId(1L);
        Assertions.assertEquals(BookMapper.INSTANCE.bookListToBookDtoList(bookList), result);
    }
}