package com.example.myregistrar.models;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class StudentTest {
    @Mock
    Date birthDate;
    @Mock
    List<Course> courses;
    @InjectMocks
    Student student = new Student();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetId() throws Exception {
        student.setId(Long.valueOf(1));
    }

    @Test
    public void testSetFirstName() throws Exception {
        student.setFirstName("firstName");
    }

    @Test
    public void testSetLastName() throws Exception {
        student.setLastName("lastName");
    }

    @Test
    public void testSetBirthDate() throws Exception {
        student.setBirthDate(new GregorianCalendar(2023, Calendar.JULY, 24, 19, 31).getTime());
    }

    @Test
    public void testSetAge() throws Exception {
        student.setAge(Integer.valueOf(0));
    }

    @Test
    public void testSetGender() throws Exception {
        student.setGender("gender");
    }

    @Test
    public void testSetEmail() throws Exception {
        student.setEmail("email");
    }

    @Test
    public void testSetCourses() throws Exception {
        student.setCourses(List.of(new Course("name", "university", "department", "instructor", Integer.valueOf(0))));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = student.equals("o");
        assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = student.canEqual("other");
        assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = student.hashCode();
        assertNotEquals(0, result);
    }

    @Test
    public void testSettersAndGetters() {
        Course course = new Course();
        course.setId(1L);
        assertEquals(1L, (long) course.getId());

        course.setName("name");
        assertEquals("name", course.getName());

        course.setUniversity("university");
        assertEquals("university", course.getUniversity());

        course.setDepartment("department");
        assertEquals("department", course.getDepartment());

        course.setInstructor("instructor");
        assertEquals("instructor", course.getInstructor());

        course.setCreditHours(3);
        assertEquals(3, (int) course.getCreditHours());

        List<Student> students = List.of(new Student("firstName", "lastName", new Date(), "gender"));
        course.setStudents(students);
        assertEquals(students, course.getStudents());

        List<Book> books = List.of(new Book("name", "author", "genre", new Date(), "publisher"));
        course.setBooks(books);
        assertEquals(books, course.getBooks());

        List<CoursePreRequisite> coursePreRequisites = List.of(new CoursePreRequisite(new CoursePreRequisiteId(1L, 1L), course, course));
        course.setCoursePreRequisiteList(coursePreRequisites);
        assertEquals(coursePreRequisites, course.getCoursePreRequisiteList());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two Course objects with the same ID
        Course course1 = new Course("name", "university", "department", "instructor", 3);
        course1.setId(1L);

        Course course2 = new Course("name", "university", "department", "instructor", 3);
        course2.setId(1L);

        // Verify that the two Course objects are equal and have the same hashCode
        assertEquals(course1, course2);
        assertEquals(course1.hashCode(), course2.hashCode());

        // Create a Course object with a different ID
        Course course3 = new Course("name", "university", "department", "instructor", 3);
        course3.setId(2L);

        // Verify that the two Course objects are not equal and have different hashCodes
        assertNotEquals(course1, course3);
        assertNotEquals(course1.hashCode(), course3.hashCode());
    }

    @Test
    public void testToString() {
        Course course = new Course();
        course.setId(1L);
        course.setName("name");
        course.setUniversity("university");
        course.setDepartment("department");
        course.setInstructor("instructor");
        course.setCreditHours(3);

        String expectedToString = "Course(id=1, name=name, university=university, department=department, instructor=instructor, creditHours=3, students=[], books=[], coursePreRequisiteList=[])";
        String result = course.toString();

        assertEquals(expectedToString, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme