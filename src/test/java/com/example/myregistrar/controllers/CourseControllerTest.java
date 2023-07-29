package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
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

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class CourseControllerTest {
    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    BookService bookService;
    @Mock
    UniversityService universityService;
    @InjectMocks
    CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.createCourse(any())).thenReturn(course);

        CourseDto result = courseController.createCourse(new CourseDto());
        Assertions.assertEquals(course.toCourseDto(), result);
    }

    @Test
    void testAssignUniversityToCourse_InvalidUniversity() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(universityService.getUniversityById(anyLong())).thenReturn(null);

        Assertions.assertThrows(UniversityNotFoundException.class, () -> courseController.assignUniversityToCourse(Long.valueOf(1), new UniversityDto()));
    }

    @Test
    void testGetAllCourses() {
        List<Course> courseList = List.of(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(courseService.getAllCourses()).thenReturn(courseList);

        List<CourseDto> result = courseController.getAllCourses();
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testAssignBookToCourse_InvalidBook() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(bookService.getBookById(anyLong())).thenReturn(null);

        Assertions.assertThrows(BookNotFoundException.class, () -> courseController.assignBookToCourse(Long.valueOf(1), new BookDto()));
    }

    @Test
    void testGetCourseById() {
        Course course = new Course("name", "department", "instructor", Integer.valueOf(0));
        when(courseService.getCourseById(anyLong())).thenReturn(course);

        CourseDto result = courseController.getCourseById(Long.valueOf(1));
        Assertions.assertEquals(course.toCourseDto(), result);
    }

    @Test
    void testGetStudentsByCourse() {
        List<Student> students = List.of(new Student("firstName", "lastName", new
                GregorianCalendar(2023, Calendar.JULY, 28, 22, 3).getTime(),
                "gender", "password", "role"));
        when(studentService.getStudentsByCourse(any())).thenReturn(students);
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));

        List<StudentDto> result = courseController.getStudentsByCourse(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetUniversityByCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(
                new Course("name", "department", "instructor", 0));

        Assertions.assertThrows(UniversityNotFoundException.class, () -> courseController.getUniversityByCourse(1L));
    }

    @Test
    void testGetStudentsByCourse_EmptyList() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));

        when(studentService.getStudentsByCourse(any())).thenReturn(Collections.emptyList());

        List<StudentDto> result = courseController.getStudentsByCourse(Long.valueOf(1));
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testAssignPreReqFromCourse_CourseNotFound() {
        when(courseService.getCourseById(anyLong())).thenReturn(null);
        Assertions.assertThrows(CourseNotFoundException.class, () -> courseController.assignPreReqFromCourse(Long.valueOf(1), new CourseDto()));
    }

    @Test
    void testAssignUniversityToCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(universityService.getUniversityById(anyLong())).thenReturn(new University("name", "country", "city"));
        when(universityService.getUniversityByNameAndCountry(anyString(), anyString())).thenReturn(new University("name", "country", "city"));

        Assertions.assertThrows(UniversityNotFoundException.class,
                () -> courseController.assignUniversityToCourse(1L, new UniversityDto()));
    }

    @Test
    void testGetBooksByCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        List<Book> bookList = List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 22, 3).getTime(), "publisher", Integer.valueOf(0)));
        when(bookService.getBooksByCourse(any())).thenReturn(bookList);

        List<BookDto> result = courseController.getBooksByCourse(Long.valueOf(1));
        Assertions.assertEquals(BookMapper.INSTANCE.bookListToBookDtoList(bookList), result);
    }

    @Test
    void testAssignBookToCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(bookService.getBookById(anyLong())).thenReturn(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 22, 3).getTime(), "publisher", Integer.valueOf(0)));

        Assertions.assertThrows(BookNotFoundException.class,
                () -> courseController.assignBookToCourse(Long.valueOf(1), new BookDto()));
    }

    @Test
    void testGetPreReqsByCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));

        List<Course> courseList = List.of(new Course("name", "department", "instructor", Integer.valueOf(0)));
        when(courseService.getCoursePreRequisitesFromCourse(any())).thenReturn(courseList);

        List<CourseDto> result = courseController.getPreReqsByCourse(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testAssignPreReqFromCourse() {
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));
        Assertions.assertThrows(CourseNotFoundException.class,
                () -> courseController.assignPreReqFromCourse(1L, new CourseDto()));
    }

    @Test
    void testGetNotifiedStudents() {
        List<Student> students = List.of(new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 28, 22, 3).getTime(), "gender", "password", "role"));
        when(studentService.getStudentsByCourse(any())).thenReturn(students);
        when(courseService.getCourseById(anyLong())).thenReturn(new Course("name", "department", "instructor", Integer.valueOf(0)));

        List<StudentDto> result = courseController.getNotifiedStudents(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }
}