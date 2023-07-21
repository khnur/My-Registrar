package com.example.myregistrar.services;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.CourseRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class CourseServiceTest {
    @Mock
    CourseRepo courseRepo;
    @InjectMocks
    CourseService courseService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCourse() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseService.createCourse(new Course("name", "university", "department", "instructor", null));
    }

    @Test
    public void testCreateCourse2() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseService.createCourse(new CourseDto("name", "university", "department", "instructor", null));
    }

    @Test
    public void testCreateRandomCourses() throws Exception {
        when(courseRepo.existsByNameAndUniversity(anyString(), anyString())).thenReturn(true);

        courseService.createRandomCourses(0);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> result = courseService.getAllCourses();
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", null)), result);
    }

    @Test
    public void testGetCoursesByName() throws Exception {
        when(courseRepo.findCoursesByName(anyString())).thenReturn(List.of(new Course("name", "university", "department", "instructor", null)));

        List<Course> result = courseService.getCoursesByName("name");
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", null)), result);
    }

    @Test
    public void testGetCoursesByUniversity() throws Exception {
        when(courseRepo.findCoursesByUniversity(anyString())).thenReturn(List.of(new Course("name", "university", "department", "instructor", null)));

        List<Course> result = courseService.getCoursesByUniversity("university");
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", null)), result);
    }

    @Test
    public void testGetCourseByNameAndUniversity() throws Exception {
        when(courseRepo.findCourseByNameAndUniversity(anyString(), anyString())).thenReturn(null);

        Course result = courseService.getCourseByNameAndUniversity("name", "university");
        Assert.assertEquals(new Course("name", "university", "department", "instructor", null), result);
    }

    @Test
    public void testGetCoursesByStudent() throws Exception {
        List<Course> result = courseService.getCoursesByStudent(new Student(null, null, null, "gender"));
        Assert.assertEquals(List.of(new Course("name", "university", "department", "instructor", null)), result);
    }

    @Test
    public void testAssignBooksToCourse() throws Exception {
        courseService.assignBooksToCourse(new Course("name", "university", "department", "instructor", null), List.of(new Book("name", "author", "genre", null, "publisher")));
    }

    @Test
    public void testAssignStudentsToCourse() throws Exception {
        courseService.assignStudentsToCourse(new Course("name", "university", "department", "instructor", null), List.of(new Student(null, null, null, "gender")));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme