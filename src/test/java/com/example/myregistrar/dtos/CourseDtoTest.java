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
    public void testSetStudentDtoList() throws Exception {
        courseDto.setStudentDtoList(List.of(new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 21, 17, 3).getTime(), "gender")));
    }

    @Test
    public void testSetBookDtoList() throws Exception {
        courseDto.setBookDtoList(List.of(
                new Book("name", "author", "genre",
                        new GregorianCalendar(2023, Calendar.JULY, 21, 17, 3).getTime(), "publisher")));
    }

    @Test
    public void testSetCoursePreRequisiteList() throws Exception {
        courseDto.setCoursePreRequisiteList(List.of(
                new CoursePreRequisite(
                        new CoursePreRequisiteId(Long.valueOf(1), Long.valueOf(1)),
                        new Course("name", "university", "department", "instructor", Integer.valueOf(0)),
                        new Course("name", "university", "department", "instructor", Integer.valueOf(0))
                ))
        );
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

    @Test
    public void testStudentDtoList() {
        CourseDto courseDto = new CourseDto();

        List<Student> studentDtos = new ArrayList<>();
        studentDtos.add(new Student("John", "Doe", new Date(), "Male"));

        courseDto.setStudentDtoList(studentDtos);

        Assert.assertEquals(studentDtos, courseDto.getStudentDtoList());
    }

    @Test
    public void testBookDtoList() {
        CourseDto courseDto = new CourseDto();

        List<Book> bookDtos = new ArrayList<>();
        bookDtos.add(new Book("Book1", "Author1", "Fiction", new Date(), "Publisher1"));

        courseDto.setBookDtoList(bookDtos);

        Assert.assertEquals(bookDtos, courseDto.getBookDtoList());
    }

    @Test
    public void testCoursePreRequisiteList() {
        CourseDto courseDto = new CourseDto();

        List<CoursePreRequisite> coursePreRequisites = new ArrayList<>();
        Course course1 = new Course("Course1", "University1", "Department1", "Instructor1", 3);
        Course course2 = new Course("Course2", "University2", "Department2", "Instructor2", 3);
        CoursePreRequisite coursePreRequisite = new CoursePreRequisite(new CoursePreRequisiteId(1L, 2L), course1, course2);
        coursePreRequisites.add(coursePreRequisite);

        courseDto.setCoursePreRequisiteList(coursePreRequisites);

        Assert.assertEquals(coursePreRequisites, courseDto.getCoursePreRequisiteList());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme