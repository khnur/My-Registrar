package com.example.myregistrar.models;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookTest {
    @Mock
    Date publishedDate;
    @Mock
    Course course;
    @InjectMocks
    Book book;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToBookDto() throws Exception {
        BookDto expectedDto = new BookDto("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 24, 19, 13).getTime(), "publisher");

        BookDto result = expectedDto.toBook().toBookDto();

        assertEquals(expectedDto, result);
    }

    @Test
    public void testSetId() throws Exception {
        book.setId(Long.valueOf(1));
    }

    @Test
    public void testSetName() throws Exception {
        book.setName("name");
    }

    @Test
    public void testSetAuthor() throws Exception {
        book.setAuthor("author");
    }

    @Test
    public void testSetGenre() throws Exception {
        book.setGenre("genre");
    }

    @Test
    public void testSetPublishedDate() throws Exception {
        book.setPublishedDate(new GregorianCalendar(2023, Calendar.JULY, 24, 19, 13).getTime());
    }

    @Test
    public void testSetPublisher() throws Exception {
        book.setPublisher("publisher");
    }

    @Test
    public void testSetCourse() throws Exception {
        book.setCourse(new Course("name", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = book.equals("o");
        assertFalse(result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = book.canEqual("other");
        assertFalse(result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = book.hashCode();
        assertNotEquals(0, result);
    }

    @Test
    public void testSettersAndGetters() {
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

        // Create a Book object with a different ID
        Book book3 = new Book("name", "author", "genre", new Date(), "publisher");
        book3.setId(2L);

        assertNotEquals(book1, book3);
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    public void testToString() {
        book.setId(1L);
        book.setName("name");
        book.setAuthor("author");
        book.setGenre("genre");
        Date publishedDate = new GregorianCalendar(2023, Calendar.JULY, 24, 19, 13).getTime();
        book.setPublishedDate(publishedDate);
        book.setPublisher("publisher");
        book.setCourse(null);

        String expectedToString = "Book(id=1, name=name, author=author, genre=genre, publishedDate=" + publishedDate + ", publisher=publisher, course=null)";
        String result = book.toString();

        assertEquals(expectedToString, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme