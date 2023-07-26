package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.BookAlreadyExistsException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.util.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookServiceImplTest {
    @Mock
    BookRepo bookRepo;
    @Mock
    Logger log;
    @InjectMocks
    BookServiceImpl bookServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher");

        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(true);

        try {
            bookServiceImpl.createBook(book);
        } catch (BookAlreadyExistsException e) {
            Assert.fail("Expected BookAlreadyExistsException not thrown.");
        }
    }

    @Test(expected = BookAlreadyExistsException.class)
    public void testCreateBook_BookAlreadyExistsException() throws Exception {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(true);

        Book book = new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher");
        bookServiceImpl.createBook(book);
    }

    @Test
    public void testCreateRandomBooks() throws Exception {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(false);

        bookServiceImpl.generateRandomBooks(5);

        verify(bookRepo, times(5)).save(any(Book.class));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("name1", "author1", "genre1", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher1"));
        bookList.add(new Book("name2", "author2", "genre2", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher2"));
        when(bookRepo.findAll()).thenReturn(bookList);

        List<Book> result = bookServiceImpl.getAllBooks();
        Assert.assertEquals(bookList, result);
    }

    @Test
    public void testGetBooksByName() throws Exception {
        when(bookRepo.findBooksByName(anyString())).thenReturn(List.of(new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher")));

        List<Book> result = bookServiceImpl.getBooksByName("name");
        Assert.assertEquals(List.of(new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher")), result);
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        when(bookRepo.findBooksByAuthor(anyString())).thenReturn(List.of(new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher")));

        List<Book> result = bookServiceImpl.getBooksByAuthor("author");
        Assert.assertEquals(List.of(new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher")), result);
    }

    @Test
    public void testGetBookByNameAndAuthor() throws Exception {
        when(bookRepo.findBookByNameAndAuthor(anyString(), anyString())).thenReturn(null);

        Book result = bookServiceImpl.getBookByNameAndAuthor("name", "author");
        Assert.assertEquals(new Book("name", "author", "genre", DateMapper.DATE_FORMAT.parse("1247-74-77"), "publisher"), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme