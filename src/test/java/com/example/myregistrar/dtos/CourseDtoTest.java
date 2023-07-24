package com.example.myregistrar.dtos;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.CoursePreRequisite;
import com.example.myregistrar.models.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class CourseDtoTest {
    @Mock
    List<StudentDto> studentDtoList;
    @Mock
    List<BookDto> bookDtoList;
    @Mock
    List<CoursePreRequisite> coursePreRequisiteList;
    @InjectMocks
    CourseDto courseDto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetters() {
        CourseDto courseDto = new CourseDto();

        Long id = 1L;
        String name = "Java Programming";
        String university = "Sample University";
        String department = "Computer Science";
        String instructor = "John Doe";
        Integer creditHours = 3;

        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setUniversity(university);
        courseDto.setDepartment(department);
        courseDto.setInstructor(instructor);
        courseDto.setCreditHours(creditHours);

        Assert.assertEquals(id, courseDto.getId());
        Assert.assertEquals(name, courseDto.getName());
        Assert.assertEquals(university, courseDto.getUniversity());
        Assert.assertEquals(department, courseDto.getDepartment());
        Assert.assertEquals(instructor, courseDto.getInstructor());
        Assert.assertEquals(creditHours, courseDto.getCreditHours());
    }
    @Test
    public void testSetId() throws Exception {
        courseDto.setId(Long.valueOf(1));
    }

    @Test
    public void testSetName() throws Exception {
        courseDto.setName("name");
    }

    @Test
    public void testSetUniversity() throws Exception {
        courseDto.setUniversity("university");
    }

    @Test
    public void testSetDepartment() throws Exception {
        courseDto.setDepartment("department");
    }

    @Test
    public void testSetInstructor() throws Exception {
        courseDto.setInstructor("instructor");
    }

    @Test
    public void testSetCreditHours() throws Exception {
        courseDto.setCreditHours(Integer.valueOf(0));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = courseDto.equals("o");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testCanEqual() {
        CourseDto courseDto1 = new CourseDto("Java Programming", "Sample University", "Computer Science", "John Doe", 3);
        CourseDto courseDto2 = new CourseDto("Java Programming", "Sample University", "Computer Science", "John Doe", 3);
        CourseDto courseDto3 = new CourseDto("Database Management", "Sample University", "Computer Science", "Jane Smith", 3);

        Assert.assertTrue(courseDto1.canEqual(courseDto2));
        Assert.assertTrue(courseDto2.canEqual(courseDto1));
        Assert.assertTrue(courseDto1.canEqual(courseDto1));
        Assert.assertFalse(courseDto1.canEqual(courseDto3));
    }


    @Test
    public void testHashCode() throws Exception {
        int result = courseDto.hashCode();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testToString() throws Exception {
        String result = courseDto.toString();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme