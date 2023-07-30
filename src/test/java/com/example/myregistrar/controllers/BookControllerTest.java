package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class BookControllerTest {
    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        Book book = new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0);
        when(bookService.createBook(any())).thenReturn(book);

        BookDto result = bookController.createBook(new BookDto());
        Assertions.assertEquals(BookMapper.INSTANCE.bookToBookDto(book), result);
    }


    @Test
    void testCreateBook_InvalidData() {
        when(bookService.createBook(any())).thenThrow(new BookNotFoundException(""));
        Assertions.assertThrows(BookNotFoundException.class, () -> bookController.createBook(new BookDto()));
    }

    @Test
    void testGetAllBooks() {
        List<Book> bookList = List.of(new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0));
        when(bookService.getAllBooks()).thenReturn(bookList);

        List<BookDto> result = bookController.getAllBooks();
        Assertions.assertEquals(bookList, BookMapper.INSTANCE.bookDtoListToBookList(result));
    }

    @Test
    void testGetBookById() {
        Book book = new Book("name", "author", "genre", LocalDate.EPOCH, "publisher", 0);
        when(bookService.getBookById(anyLong())).thenReturn(book);

        BookDto result = bookController.getBookById(1L);
        Assertions.assertEquals(book, BookMapper.INSTANCE.bookDtoToBook(result));
    }

}