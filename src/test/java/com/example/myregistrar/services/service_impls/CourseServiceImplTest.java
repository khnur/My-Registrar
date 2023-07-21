package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.CoursePreRequisite;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.CoursePreRequiteRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.util.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

public class CourseServiceImplTest {
    @Mock
    CourseRepo courseRepo;
    @Mock
    CoursePreRequiteRepo coursePreRequiteRepo;
    @Mock
    Logger log;
    @InjectMocks
    CourseServiceImpl courseServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCourse() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseServiceImpl.createCourse(new Course("name", "university", null, null, null));
    }

    @Test(expected = CourseAlreadyExistsException.class)
    public void testCreateCourse_CourseAlreadyExistsException() {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseServiceImpl.createCourse(new Course("name", "university", null, null, null));
    }

    @Test
    public void testCreateCourse2() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseServiceImpl.createCourse(new CourseDto("nameDto", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test(expected = CourseAlreadyExistsException.class)
    public void testCreateCourse2_CourseAlreadyExistsException() {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseServiceImpl.createCourse(new CourseDto("nameDto", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test
    public void testCreateRandomCourses() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseServiceImpl.createRandomCourses(5);

        verify(courseRepo, times(5)).save(any(Course.class));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("name1", "university1", null, null, null));
        courseList.add(new Course("name2", "university1", null, null, null));
        when(courseRepo.findAll()).thenReturn(courseList);

        List<Course> result = courseServiceImpl.getAllCourses();
        Assert.assertEquals(courseList, result);
    }

    @Test
    public void testGetCoursesByName() throws Exception {
        when(courseRepo.findCoursesByName(anyString())).thenReturn(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))));

        List<Course> result = courseServiceImpl.getCoursesByName("name");
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))), result);
    }

    @Test(expected = CourseNotFoundException.class)
    public void testGetAllCourses_CourseNotFoundException() {
        when(courseRepo.findAll()).thenReturn(new ArrayList<>());

        courseServiceImpl.getAllCourses();
    }

    @Test
    public void testGetCoursesByUniversity() throws Exception {
        when(courseRepo.findCoursesByUniversity(anyString())).thenReturn(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))));

        List<Course> result = courseServiceImpl.getCoursesByUniversity("university");
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))), result);
    }

    @Test
    public void testGetCourseByNameAndUniversity() throws Exception {
        when(courseRepo.findCourseByNameAndUniversity(anyString(), anyString())).thenReturn(null);

        Course result = courseServiceImpl.getCourseByNameAndUniversity("name", "university");
        Assert.assertEquals(new Course("name", "university", "department", "instructor", Integer.valueOf(0)), result);
    }

    @Test
    public void testGetCoursesByStudent() throws Exception {
        List<Course> result = courseServiceImpl.getCoursesByStudent(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender"));
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))), result);
    }

    @Test
    public void testAssignBooksToCourse() throws Exception {
        Course course = new Course("name", "university", "department", "instructor", Integer.valueOf(0));
        List<Book> books = List.of(
                new Book("book1", "author1", "genre1", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher1"),
                new Book("book2", "author2", "genre2", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher2")
        );

        courseServiceImpl.assignBooksToCourse(course, books);
        Assert.assertEquals(books.size(), course.getBooks().size());

        books.forEach(book -> {
            Assert.assertTrue(course.getBooks().contains(book));
            Assert.assertEquals(course, book.getCourse());
        });
    }

    @Test
    public void testAssignStudentsToCourse() throws Exception {
        Course course = new Course("name", "university", "department", "instructor", Integer.valueOf(0));
        List<Student> students = List.of(
                new Student("firstName1", "lastName1", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender1"),
                new Student("firstName2", "lastName2", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender2")
        );

        courseServiceImpl.assignStudentsToCourse(course, students);
        Assert.assertEquals(students.size(), course.getStudents().size());

        students.forEach(student -> {
            Assert.assertTrue(course.getStudents().contains(student));
            Assert.assertTrue(student.getCourses().contains(course));
        });
    }

    @Test
    public void testRemoveCoursePreRequisiteFromCourse() throws Exception {
        Course course = new Course("name1", "university1", null, null, null);
        Course coursePreReq = new Course("name2", "university2", null, null, null);
        CoursePreRequisiteId coursePreRequisiteId = new CoursePreRequisiteId(course.getId(), coursePreReq.getId());
        CoursePreRequisite coursePreRequisite = new CoursePreRequisite(coursePreRequisiteId, course, coursePreReq);

        when(coursePreRequiteRepo.findById(coursePreRequisiteId)).thenReturn(java.util.Optional.of(coursePreRequisite));

        courseServiceImpl.removeCoursePreRequisiteFromCourse(course, coursePreReq);

        verify(coursePreRequiteRepo, times(1)).delete(coursePreRequisite);}

    @Test
    public void testAssignCoursePreRequisiteCourse() throws Exception {
        courseServiceImpl.assignCoursePreRequisiteCourse(new Course("name", "university", "department", "instructor", Integer.valueOf(0)), new Course("name", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test(expected = CourseNotFoundException.class)
    public void testRemoveCoursePreRequisiteFromCourse_CourseNotFoundException() {
        Course course = new Course("name1", "university1", null, null, null);
        Course coursePreReq = new Course("name2", "university2", null, null, null);
        CoursePreRequisiteId coursePreRequisiteId = new CoursePreRequisiteId(course.getId(), coursePreReq.getId());

        when(coursePreRequiteRepo.findById(coursePreRequisiteId)).thenReturn(java.util.Optional.empty());

        courseServiceImpl.removeCoursePreRequisiteFromCourse(course, coursePreReq);
    }

    @Test
    public void testGetCoursePreRequisitesFromCourse() throws Exception {
        when(coursePreRequiteRepo.findPrerequisiteCoursesByCourse(any())).thenReturn(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))));

        List<Course> result = courseServiceImpl.getCoursePreRequisitesFromCourse(new Course("name", "university", "department", "instructor", Integer.valueOf(0)));
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme