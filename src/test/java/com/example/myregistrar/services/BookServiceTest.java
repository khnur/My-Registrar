package com.example.myregistrar.services;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class BookServiceTest {
    @Mock
    BookRepo bookRepo;
    @InjectMocks
    BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(true);

        bookService.createBook(new Book("name", "author", "genre", null, "publisher"));
    }

    @Test
    public void testCreateBook2() throws Exception {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(true);

        bookService.createBook(new BookDto("name", "author", "genre", null, "publisher"));
    }

    @Test
    public void testCreateRandomBooks() throws Exception {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(true);

        bookService.createRandomBooks(0);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> result = bookService.getAllBooks();
        Assert.assertEquals(List.of(new Book("name", "author", "genre", null, "publisher")), result);
    }

    @Test
    public void testGetBooksByName() throws Exception {
        when(bookRepo.findBooksByName(anyString())).thenReturn(List.of(new Book("name", "author", "genre", null, "publisher")));

        List<Book> result = bookService.getBooksByName("name");
        Assert.assertEquals(List.of(new Book("name", "author", "genre", null, "publisher")), result);
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        when(bookRepo.findBooksByAuthor(anyString())).thenReturn(List.of(new Book("name", "author", "genre", null, "publisher")));

        List<Book> result = bookService.getBooksByAuthor("author");
        Assert.assertEquals(List.of(new Book("name", "author", "genre", null, "publisher")), result);
    }

    @Test
    public void testGetBookByNameAndAuthor() throws Exception {
        when(bookRepo.findBookByNameAndAuthor(anyString(), anyString())).thenReturn(null);

        Book result = bookService.getBookByNameAndAuthor("name", "author");
        Assert.assertEquals(new Book("name", "author", "genre", null, "publisher"), result);
    }

    @Test
    public void testGetBooksByStudent() throws Exception {
        when(bookRepo.findBooksByCourseIn(any())).thenReturn(List.of(new Book("name", "author", "genre", null, "publisher")));

        List<Book> result = bookService.getBooksByStudent(new Student(null, null, null, "gender"));
        Assert.assertEquals(List.of(new Book("name", "author", "genre", null, "publisher")), result);
    }

    @Test
    public void testGetBooksByCourse() throws Exception {
        when(bookRepo.findBooksByCourse(any())).thenReturn(List.of(new Book("name", "author", "genre", null, "publisher")));

        List<Book> result = bookService.getBooksByCourse(new Course("name", "university", "department", "instructor", null));
        Assert.assertEquals(List.of(new Book("name", "author", "genre", null, "publisher")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme