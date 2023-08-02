package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class CourseControllerTest {
    @Mock
    StudentFacade studentService;
    @Mock
    CourseFacade courseService;
    @Mock
    BookFacade bookService;
    @Mock
    UniversityFacade universityService;
    @InjectMocks
    CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.createCourse(any())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        CourseDto result = courseController.createCourse(new CourseDto()).getObject();
        Assertions.assertEquals(CourseMapper.INSTANCE.courseToCourseDto(course), result);
    }

    @Test
    void testAssignUniversityToCourse_InvalidUniversity() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        when(universityService.getUniversityById(anyLong())).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> courseController.assignUniversityToCourse(1L, new UniversityDto()));
    }

    @Test
    void testGetAllCourses() {
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getAllCourses()).thenReturn(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList));

        List<CourseDto> result = courseController.getAllCourses();
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testAssignBookToCourse_InvalidBook() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        when(bookService.getBookById(anyLong())).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> courseController.assignBookToCourse(1L, new BookDto()));
    }

    @Test
    void testGetCourseById() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        CourseDto result = courseController.getCourseById(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseToCourseDto(course), result);
    }

    @Test
    void testGetStudentsByCourse() {
        List<Student> students = List.of(new Student("firstName", "lastName",
                LocalDate.EPOCH, "gender"));
        when(studentService.getStudentsByCourse(any())).thenReturn(StudentMapper.INSTANCE.studentListToStudentDtoList(students));
        Course course = new Course("name", "department", "instructor", 0);

        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        List<StudentDto> result = courseController.getStudentsByCourse(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetUniversityByCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        Assertions.assertDoesNotThrow(() -> courseController.getUniversityByCourse(1L));
    }

    @Test
    void testGetStudentsByCourse_EmptyList() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        when(studentService.getStudentsByCourse(any())).thenReturn(Collections.emptyList());

        List<StudentDto> result = courseController.getStudentsByCourse(1L);
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testAssignPreReqFromCourse_CourseNotFound() {
        when(courseService.getCourseById(anyLong())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> courseController.assignPreReqFromCourse(1L, new CourseDto()));
    }

    @Test
    void testAssignUniversityToCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        University university = new University("name", "country", "city");
        when(universityService.getUniversityById(anyLong())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        Assertions.assertDoesNotThrow(() -> courseController.assignUniversityToCourse(1L, new UniversityDto()));
    }

    @Test
    void testGetBooksByCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        List<Book> bookList = List.of(new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0));
        when(bookService.getBooksByCourse(any())).thenReturn(BookMapper.INSTANCE.bookListToBookDtoList(bookList));

        List<BookDto> result = courseController.getBooksByCourse(1L);
        Assertions.assertEquals(BookMapper.INSTANCE.bookListToBookDtoList(bookList), result);
    }

    @Test
    void testAssignBookToCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        Book book = new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0);
        when(bookService.getBookById(anyLong())).thenReturn(BookMapper.INSTANCE.bookToBookDto(book));

        Assertions.assertDoesNotThrow(() -> courseController.assignBookToCourse(1L, new BookDto()));
    }

    @Test
    void testGetPreReqsByCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getPreReqsByCourse(any())).thenReturn(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList));

        List<CourseDto> result = courseController.getPreReqsByCourse(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }

    @Test
    void testAssignPreReqFromCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));
        Assertions.assertDoesNotThrow(() -> courseController.assignPreReqFromCourse(1L, new CourseDto()));
    }

    @Test
    void testGetNotifiedStudents() {
        List<Student> students = List.of(new Student("firstName", "lastName", LocalDate.EPOCH, "gender"));
        when(studentService.getNotifiedStudents(any())).thenReturn(StudentMapper.INSTANCE.studentListToStudentDtoList(students));

        Course course = new Course("name", "department", "instructor", 0);
        when(courseService.getCourseById(anyLong())).thenReturn(CourseMapper.INSTANCE.courseToCourseDto(course));

        List<StudentDto> result = courseController.getNotifiedStudents(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }
}