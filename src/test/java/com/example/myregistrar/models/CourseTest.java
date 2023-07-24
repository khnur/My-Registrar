package com.example.myregistrar.models;

import com.example.myregistrar.dtos.CourseDto;
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

public class CourseTest {
    @Mock
    List<Student> students;
    @Mock
    List<Book> books;
    @Mock
    List<CoursePreRequisite> coursePreRequisiteList;
    @InjectMocks
    Course course;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToCourseDto() throws Exception {
        CourseDto result = course.toCourseDto();
        assertEquals(new CourseDto(), result);
    }

    @Test
    public void testSetId() throws Exception {
        course.setId(Long.valueOf(1));
    }

    @Test
    public void testSetName() throws Exception {
        course.setName("name");
    }

    @Test
    public void testSetUniversity() throws Exception {
        course.setUniversity("university");
    }

    @Test
    public void testSetDepartment() throws Exception {
        course.setDepartment("department");
    }

    @Test
    public void testSetInstructor() throws Exception {
        course.setInstructor("instructor");
    }

    @Test
    public void testSetCreditHours() throws Exception {
        course.setCreditHours(Integer.valueOf(0));
    }

    @Test
    public void testSetStudents() throws Exception {
        course.setStudents(List.of(new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 24, 19, 25).getTime(), "gender")));
    }

    @Test
    public void testSetBooks() throws Exception {
        course.setBooks(List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 24, 19, 25).getTime(), "publisher")));
    }

    @Test
    public void testSetCoursePreRequisiteList() throws Exception {
        course.setCoursePreRequisiteList(List.of(new CoursePreRequisite(new CoursePreRequisiteId(Long.valueOf(1), Long.valueOf(1)), new Course("name", "university", "department", "instructor", Integer.valueOf(0)), new Course("name", "university", "department", "instructor", Integer.valueOf(0)))));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = course.equals("o");
        assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = course.canEqual("other");
        assertNotEquals(true, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = course.hashCode();
        assertNotEquals(0, result);
    }


    @Test
    public void testSettersAndGetters() {
        // Test setters
        Book book = new Book();
        book.setId(1L);
        assertEquals(1L, (long) book.getId());

        book.setName("name");
        assertEquals("name", book.getName());

        book.setAuthor("author");
        assertEquals("author", book.getAuthor());

        book.setGenre("genre");
        assertEquals("genre", book.getGenre());

        Date publishedDate = new GregorianCalendar(2023, Calendar.JULY, 24, 19, 13).getTime();
        book.setPublishedDate(publishedDate);
        assertEquals(publishedDate, book.getPublishedDate());

        book.setPublisher("publisher");
        assertEquals("publisher", book.getPublisher());

        Course course = new Course("name", "university", "department", "instructor", 0);
        book.setCourse(course);
        assertEquals(course, book.getCourse());
    }

    @Test
    public void testEqualsAndHashCode() {
        Book book1 = new Book("name", "author", "genre", new Date(), "publisher");
        book1.setId(1L);

        Book book2 = new Book("name", "author", "genre", new Date(), "publisher");
        book2.setId(1L);

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());

        Book book3 = new Book("name", "author", "genre", new Date(), "publisher");
        book3.setId(2L);

        assertNotEquals(book1, book3);
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }
    @Test
    public void testToString() {
        Book book = new Book();
        book.setId(1L);
        book.setName("name");
        book.setAuthor("author");
        book.setGenre("genre");
        Date publishedDate = new GregorianCalendar(2023, Calendar.JULY, 24, 19, 13).getTime();
        book.setPublishedDate(publishedDate);
        book.setPublisher("publisher");

        String expectedToString = "Book(id=1, name=name, author=author, genre=genre, publishedDate=" + publishedDate + ", publisher=publisher, course=null)";
        String result = book.toString();

        assertEquals(expectedToString, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme