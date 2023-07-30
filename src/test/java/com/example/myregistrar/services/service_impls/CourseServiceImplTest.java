package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.jms.KafkaService;
import com.example.myregistrar.models.*;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.util.NewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {
    @Mock
    CourseRepo courseRepo;
    @Mock
    CoursePreRequiteRepo coursePreRequiteRepo;
    @Mock
    StudentRepo studentRepo;
    @Mock
    BookRepo bookRepo;
    @Mock
    KafkaService kafkaService;
    @InjectMocks
    CourseServiceImpl courseServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        Course courseToSave = new Course("name", "department", "instructor", 0);

        Course savedCourse = new Course("name", "department", "instructor", 0);
        savedCourse.setId(1L);

        when(courseRepo.save(courseToSave)).thenReturn(savedCourse);

        Course result = courseServiceImpl.createCourse(courseToSave);

        assertEquals(savedCourse, result);
    }

    @Test
    void testCreateCourse_NullInput() {
        assertThrows(CourseNotFoundException.class, () -> courseServiceImpl.createCourse(null));
    }

    @Test
    void testGenerateRandomCourses() {
        int n = 5;
        List<Course> randomCourses = IntStream.range(0, n)
                .mapToObj(i -> NewModel.createRandomCourse())
                .toList();

        when(courseRepo.save(any())).thenAnswer(invocation -> {
            Course courseToSave = invocation.getArgument(0);

            Course saved = new Course(courseToSave.getName(), courseToSave.getDepartment(), courseToSave.getInstructor(), courseToSave.getCreditHours());
            saved.setId(1L);
            return saved;
        });

        courseServiceImpl.generateRandomCourses(n);

        verify(courseRepo, times(n)).save(any());
    }

    @Test
    void testGetCourseById() {
        Long courseId = 1L;

        Course expectedCourse = new Course("name", "department", "instructor", 0);
        expectedCourse.setId(courseId);

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        Course result = courseServiceImpl.getCourseById(courseId);

        assertEquals(expectedCourse, result);
    }

    @Test
    void testGetAllCourses() {
        List<Course> expectedCourses = List.of(new Course("name", "department", "instructor", 0));

        when(courseRepo.findAll()).thenReturn(expectedCourses);

        List<Course> result = courseServiceImpl.getAllCourses();

        assertEquals(expectedCourses, result);
    }

    @Test
    void testGetCoursesByName() {
        String courseName = "name";

        List<Course> expectedCourses = List.of(new Course("name", "department", "instructor", 0));

        when(courseRepo.findCoursesByName(courseName)).thenReturn(expectedCourses);

        List<Course> result = courseServiceImpl.getCoursesByName(courseName);

        assertEquals(expectedCourses, result);
    }

    @Test
    void testGetCoursesByNameAndDepartment() {
        String courseName = "name";
        String courseDepartment = "department";

        Course expectedCourse = new Course("name", "department", "instructor", 0);

        when(courseRepo.findCourseByNameAndDepartment(courseName, courseDepartment)).thenReturn(Optional.of(expectedCourse));

        Course result = courseServiceImpl.getCoursesByNameAndDepartment(courseName, courseDepartment);

        assertEquals(expectedCourse, result);
    }

    @Test
    void testGetCoursesByUniversity() {
        University university = new University("name", "country", "city");
        university.setId(1L);

        List<Course> expectedCourses = List.of(
                new Course("name1", "department1", "instructor1", 0),
                new Course("name2", "department2", "instructor2", 0)
        );

        when(courseRepo.findCoursesByUniversityId(university.getId())).thenReturn(expectedCourses);

        List<Course> result = courseServiceImpl.getCoursesByUniversity(university);

        assertEquals(expectedCourses, result);
    }

    @Test
    void testGetCoursesByUniversity_NullPoint() {
        University university = new University("name", "country", "city");

        List<Course> expectedCourses = List.of(
                new Course("name1", "department1", "instructor1", 0),
                new Course("name2", "department2", "instructor2", 0)
        );

        when(courseRepo.findCoursesByUniversityId(university.getId())).thenReturn(expectedCourses);

        assertThrows(UniversityNotFoundException.class,
                () -> courseServiceImpl.getCoursesByUniversity(university));

    }

    @Test
    void testGetCoursesByStudent() {
        Long studentId = 1L;

        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        student.setId(studentId);

        List<Course> expectedCourses = List.of(new Course("name", "department", "instructor", 0));

        when(courseRepo.findCoursesByStudentId(studentId)).thenReturn(expectedCourses);

        List<Course> result = courseServiceImpl.getCoursesByStudent(student);

        assertEquals(expectedCourses, result);
    }

    @Test
    void testGetCoursesByStudent_NullPoint() {
        Long studentId = 1L;

        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");

        List<Course> expectedCourses = List.of(new Course("name", "department", "instructor", 0));

        when(courseRepo.findCoursesByStudentId(studentId)).thenReturn(expectedCourses);

        assertThrows(StudentNotFoundException.class,
                () -> courseServiceImpl.getCoursesByStudent(student));
    }

    @Test
    void testAssignBookToCourse() {
        Long courseId = 1L;
        Long bookId = 1L;

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(courseId);

        Book book = new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", Integer.valueOf(0));
        book.setId(bookId);

        List<Book> expectedBooks = new ArrayList<>();

        when(bookRepo.findBooksByCourseId(courseId)).thenReturn(expectedBooks);

        courseServiceImpl.assignBookToCourse(course, book);

        verify(courseRepo, times(1)).save(course);
    }

    @Test
    void testAssignBookToCourse_NullPoint() {
        Long courseId = 1L;

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(courseId);

        Book book = new Book("name", "author", "genre",LocalDate.EPOCH, "publisher", 0);

        List<Book> expectedBooks = new ArrayList<>();

        when(bookRepo.findBooksByCourseId(courseId)).thenReturn(expectedBooks);

        assertThrows(NoSuchElementException.class,
                () -> courseServiceImpl.assignBookToCourse(course, book));
    }

    @Test
    void testAssignStudentToCourse() {
        when(courseRepo.findCoursesByStudentId(anyLong())).thenReturn(new ArrayList<>());
        when(studentRepo.findStudentsByCourseId(anyLong())).thenReturn(new ArrayList<>());

        University university = new University();
        university.setId(1L);

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(1L);
        course.setUniversity(university);

        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        student.setId(1L);
        student.setUniversity(university);

        courseServiceImpl.assignStudentToCourse(course, student);

        assertEquals(1, course.getStudents().size());
        assertTrue(course.getStudents().contains(student));

        assertEquals(1, student.getCourses().size());
        assertTrue(student.getCourses().contains(course));
    }

    @Test
    void testAssignStudentToCourse_NullPoint() {
        when(courseRepo.findCoursesByStudentId(anyLong())).thenReturn(new ArrayList<>());
        when(studentRepo.findStudentsByCourseId(anyLong())).thenReturn(new ArrayList<>());

        University university = new University();

        Course course = new Course("name", "department", "instructor", 0);
        course.setUniversity(university);

        Student student = new Student("firstName", "lastName", LocalDate.EPOCH, "gender");
        student.setId(1L);
        student.setUniversity(university);

        assertThrows(CourseNotFoundException.class,
                () -> courseServiceImpl.assignStudentToCourse(course, student));
    }

    @Test
    void testRemoveCoursePreRequisiteFromCourse() {
        Long courseId = 1L;
        Long coursePreReqId = 2L;

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(courseId);
        Course coursePreReq = new Course("name", "department", "instructor", 0);
        coursePreReq.setId(coursePreReqId);

        CoursePreRequisiteId id = new CoursePreRequisiteId(courseId, coursePreReqId);
        course.getCoursePreRequisiteList().add(new CoursePreRequisite(id, course, coursePreReq));

        assertEquals(1, course.getCoursePreRequisiteList().size());
        assertTrue(course.getCoursePreRequisiteList().contains(new CoursePreRequisite(id, course, coursePreReq)));

        courseServiceImpl.removeCoursePreRequisiteFromCourse(course, coursePreReq);

        assertEquals(1, course.getCoursePreRequisiteList().size());
    }

    @Test
    void testRemoveCoursePreRequisiteFromCourse_NullPoint() {
        Long courseId = 1L;
        Long coursePreReqId = 2L;

        Course course = new Course("name", "department", "instructor", 0);
        Course coursePreReq = new Course("name", "department", "instructor", 0);

        CoursePreRequisiteId id = new CoursePreRequisiteId(courseId, coursePreReqId);
        course.getCoursePreRequisiteList().add(new CoursePreRequisite(id, course, coursePreReq));

        assertThrows(NoSuchElementException.class,
                () -> courseServiceImpl.removeCoursePreRequisiteFromCourse(course, coursePreReq));
    }

    @Test
    void testAssignCoursePreRequisiteCourse() {
        Long courseId = 1L;
        Long coursePreReqId = 2L;
        CoursePreRequisiteId id = new CoursePreRequisiteId(courseId, coursePreReqId);

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(courseId);
        Course coursePreReq = new Course("name", "department", "instructor", 0);
        coursePreReq.setId(coursePreReqId);

        University university = new University("universityName", "country", "city");
        university.setId(1L);

        CoursePreRequisite coursePreRequite = new CoursePreRequisite(id, course, coursePreReq);

        course.setUniversity(university);
        coursePreReq.setUniversity(university);

        assertEquals(0, course.getCoursePreRequisiteList().size());

        courseServiceImpl.assignCoursePreRequisiteCourse(course, coursePreReq);

        course.getCoursePreRequisiteList().add(coursePreRequite);

        assertEquals(1, course.getCoursePreRequisiteList().size());
        assertTrue(course.getCoursePreRequisiteList().contains(coursePreRequite));
    }

    @Test
    void testGetCoursePreRequisitesFromCourse() {
        Long courseId = 1L;
        Long coursePreReqId = 2L;

        Course course = new Course("name", "department", "instructor", 0);
        course.setId(courseId);
        Course coursePreReq = new Course("name", "department", "instructor", 0);
        coursePreReq.setId(coursePreReqId);

        University university = new University("universityName", "country", "city");
        university.setId(1L);

        course.setUniversity(university);
        coursePreReq.setUniversity(university);

        CoursePreRequisiteId coursePreRequisiteId = new CoursePreRequisiteId(course.getId(), coursePreReq.getId());
        CoursePreRequisite coursePreRequisite = new CoursePreRequisite(coursePreRequisiteId, course, coursePreReq);

        course.getCoursePreRequisiteList().add(coursePreRequisite);

        when(coursePreRequiteRepo.findPrerequisiteCoursesByCourseId(course.getId())).thenReturn(List.of(coursePreReq));

        List<Course> result = courseServiceImpl.getCoursePreRequisitesFromCourse(course);

        assertEquals(1, result.size());
        assertTrue(result.contains(coursePreReq));
    }

    @Test
    void testAssignUniversityToCourse() {
        Course course = new Course("name", "department", "instructor", 0);
        course.setId(1L);
        University university = new University("name", "country", "city");
        university.setId(1L);

        courseServiceImpl.assignUniversityToCourse(course, university);

        assertEquals(university, course.getUniversity());
    }

    @Test
    void testAssignUniversityToCourse_NullPoint() {
        Course course = new Course("name", "department", "instructor", 0);

        University university = new University("name", "country", "city");

        assertThrows(NoSuchElementException.class,
                () -> courseServiceImpl.assignUniversityToCourse(course, university));
    }

    @Test
    void testNotifyStudentsWithinUniversity_ValidCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setUniversity(new University());

        doNothing().when(kafkaService).sendToCourseTopic(any(CourseDto.class));

        courseServiceImpl.notifyStudentsWithinUniversity(course);

        verify(kafkaService, times(1)).sendToCourseTopic(any(CourseDto.class));
    }

    @Test
    void testNotifyStudentsWithinUniversity_NullCourse() {
        Assertions.assertThrows(CourseNotFoundException.class, () -> courseServiceImpl.notifyStudentsWithinUniversity(null));

        verify(kafkaService, never()).sendToCourseTopic(any(CourseDto.class));
    }

    @Test
    void testNotifyStudentsWithinUniversity_UnregisteredUniversity() {
        Course course = new Course();
        course.setId(1L);

        Assertions.assertThrows(UniversityNotFoundException.class, () -> courseServiceImpl.notifyStudentsWithinUniversity(course));

        verify(kafkaService, never()).sendToCourseTopic(any(CourseDto.class));
    }
}