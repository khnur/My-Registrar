package com.example.myregistrar.dtos;

import com.example.myregistrar.util.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

public class BookDtoTest {
    @Mock
    Date publishedDate;
    @Mock
    CourseDto courseDto;
    @InjectMocks
    BookDto bookDto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetters() {
        BookDto bookDto = new BookDto();

        Long id = 1L;
        String name = "The Book";
        String author = "John Doe";
        String genre = "Fiction";
        Date publishedDate = new GregorianCalendar(2023, Calendar.JULY, 21, 16, 48).getTime();
        String publisher = "ABC Publications";

        bookDto.setId(id);
        bookDto.setName(name);
        bookDto.setAuthor(author);
        bookDto.setGenre(genre);
        bookDto.setPublishedDate(publishedDate);
        bookDto.setPublisher(publisher);

        Assert.assertEquals(id, bookDto.getId());
        Assert.assertEquals(name, bookDto.getName());
        Assert.assertEquals(author, bookDto.getAuthor());
        Assert.assertEquals(genre, bookDto.getGenre());
        Assert.assertEquals(publishedDate, bookDto.getPublishedDate());
        Assert.assertEquals(publisher, bookDto.getPublisher());
    }

    @Test
    public void testSetId() throws Exception {
        bookDto.setId(Long.valueOf(1));
    }

    @Test
    public void testSetName() throws Exception {
        bookDto.setName("name");
    }

    @Test
    public void testSetAuthor() throws Exception {
        bookDto.setAuthor("author");
    }

    @Test
    public void testSetGenre() throws Exception {
        bookDto.setGenre("genre");
    }

    @Test
    public void testSetPublishedDate() throws Exception {
        bookDto.setPublishedDate(new GregorianCalendar(2023, Calendar.JULY, 21, 16, 48).getTime());
    }

    @Test
    public void testSetPublisher() throws Exception {
        bookDto.setPublisher("publisher");
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = bookDto.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() {
        BookDto bookDto1 = new BookDto("Book1", "Author1", "Fiction", new Date(), "Publisher1");
        BookDto bookDto2 = new BookDto("Book1", "Author1", "Fiction", new Date(), "Publisher1");
        BookDto bookDto3 = new BookDto("Book2", "Author2", "Non-Fiction", new Date(), "Publisher2");

        Assert.assertTrue(bookDto1.canEqual(bookDto2));
        Assert.assertTrue(bookDto2.canEqual(bookDto1));
        Assert.assertTrue(bookDto1.canEqual(bookDto1));
        Assert.assertFalse(bookDto1.canEqual(bookDto3));
    }

    @Test
    public void testHashCode() throws Exception {
        int result = bookDto.hashCode();
        Assert.assertEquals(0, result);
    }


    @Test
    public void testToString() {
        BookDto bookDto = new BookDto();

        String name = "The Book";
        String author = "John Doe";
        String genre = "Fiction";
        Date publishedDate = new GregorianCalendar(2023, Calendar.JULY, 21, 16, 48).getTime();
        String publisher = "ABC Publications";

        bookDto.setName(name);
        bookDto.setAuthor(author);
        bookDto.setGenre(genre);
        bookDto.setPublishedDate(publishedDate);
        bookDto.setPublisher(publisher);

        String expectedToStringResult = "BookDto(id=null, name=The Book, author=John Doe, genre=Fiction, publishedDate=" +
                DateMapper.DATE_FORMAT.format(publishedDate) + ", publisher=ABC Publications)";

        Assert.assertEquals(expectedToStringResult, bookDto.toString());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme