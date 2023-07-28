package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.BookAlreadyExistsException;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    @Mock
    BookRepo bookRepo;
    @InjectMocks
    BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_Successful() {
        Book bookToCreate = new Book("name", "author", null, null, null, null);
        when(bookRepo.existsByNameAndAuthor(bookToCreate.getName(), bookToCreate.getAuthor())).thenReturn(false);
        when(bookRepo.save(any(Book.class))).thenReturn(bookToCreate);

        Book createdBook = bookServiceImpl.createBook(bookToCreate);

        assertNotNull(createdBook);
        assertEquals(bookToCreate.getName(), createdBook.getName());
        assertEquals(bookToCreate.getAuthor(), createdBook.getAuthor());
        verify(bookRepo, times(1)).existsByNameAndAuthor(bookToCreate.getName(), bookToCreate.getAuthor());
        verify(bookRepo, times(1)).save(bookToCreate);
    }

    @Test
    void testCreateBook_NullInput() {
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.createBook(null));
    }

    @Test
    void testCreateBook_BookAlreadyExists() {
        Book existingBook = new Book("name", "author", null, null, null, null);
        when(bookRepo.existsByNameAndAuthor(existingBook.getName(), existingBook.getAuthor())).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookServiceImpl.createBook(existingBook));
        verify(bookRepo, times(1)).existsByNameAndAuthor(existingBook.getName(), existingBook.getAuthor());
        verify(bookRepo, never()).save(existingBook);
    }

    @Test
    void testCreateBook_SameNameDifferentAuthor() {
        Book existingBook = new Book("name", "author", null, null, null, null);
        Book newBookWithSameName = new Book("name", "author", null, null, null, null);
        when(bookRepo.existsByNameAndAuthor(existingBook.getName(), existingBook.getAuthor())).thenReturn(false);
        when(bookRepo.save(any(Book.class))).thenReturn(newBookWithSameName);

        Book createdBook = bookServiceImpl.createBook(newBookWithSameName);

        assertNotNull(createdBook);
        assertEquals(newBookWithSameName.getName(), createdBook.getName());
        assertEquals(newBookWithSameName.getAuthor(), createdBook.getAuthor());
        verify(bookRepo, times(1)).existsByNameAndAuthor(existingBook.getName(), existingBook.getAuthor());
        verify(bookRepo, times(1)).save(newBookWithSameName);
    }


    @Test
    void testCreateBook_UniqueNameAndAuthor() {
        Book uniqueBook = new Book("name", "author", null, null, null, null);
        when(bookRepo.existsByNameAndAuthor(uniqueBook.getName(), uniqueBook.getAuthor())).thenReturn(false);
        when(bookRepo.save(any(Book.class))).thenReturn(uniqueBook);

        Book createdBook = bookServiceImpl.createBook(uniqueBook);

        assertNotNull(createdBook);
        assertEquals(uniqueBook.getName(), createdBook.getName());
        assertEquals(uniqueBook.getAuthor(), createdBook.getAuthor());
        verify(bookRepo, times(1)).existsByNameAndAuthor(uniqueBook.getName(), uniqueBook.getAuthor());
        verify(bookRepo, times(1)).save(uniqueBook);
    }

    @Test
    void testGenerateRandomBooks() {
        when(bookRepo.existsByNameAndAuthor(anyString(), anyString())).thenReturn(false);

        int numberOfRandomBooksToGenerate = 5;
        bookServiceImpl.generateRandomBooks(numberOfRandomBooksToGenerate);

        verify(bookRepo, times(numberOfRandomBooksToGenerate)).existsByNameAndAuthor(anyString(), anyString());
        verify(bookRepo, times(numberOfRandomBooksToGenerate)).save(any(Book.class));
    }

    @Test
    void testGetAllBooks_WhenNoBooksExist() {
        when(bookRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getAllBooks());
    }

    @Test
    void testGetAllBooks_WhenBooksExist() {
        List<Book> mockBookList = List.of(
                new Book(
                        "name",
                        "author",
                        "genre",
                        new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(),
                        "publisher",
                        0)
        );
        when(bookRepo.findAll()).thenReturn(mockBookList);

        List<Book> result = bookServiceImpl.getAllBooks();

        assertEquals(mockBookList, result);
    }

    @Test
    void testGetBookById() {
        long bookId = 1L;
        Book mockBook = new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0);
        when(bookRepo.findById(bookId)).thenReturn(Optional.of(mockBook));

        Book result = bookServiceImpl.getBookById(bookId);

        assertEquals(mockBook, result);
    }

    @Test
    void testGetBooksByName_BooksExist() {
        List<Book> mockBookList = List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0));
        when(bookRepo.findBooksByName("name")).thenReturn(mockBookList);

        List<Book> result = bookServiceImpl.getBooksByName("name");

        assertEquals(mockBookList, result);
    }

    @Test
    void testGetBooksByName_BooksDoNotExist() {
        when(bookRepo.findBooksByName("nonExistentName")).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBooksByName("nonExistentName"));
    }

    @Test
    void testGetBooksByName_WhenEmptyResult() {
        when(bookRepo.findBooksByName("emptyResult")).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBooksByName("emptyResult"));
    }

    @Test
    void testGetBooksByAuthor_BooksExist() {
        List<Book> mockBookList = List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0));
        when(bookRepo.findBooksByAuthor("author")).thenReturn(mockBookList);

        List<Book> result = bookServiceImpl.getBooksByAuthor("author");

        assertEquals(mockBookList, result);
    }

    @Test
    void testGetBooksByAuthor_BooksDoNotExist() {
        when(bookRepo.findBooksByAuthor("nonExistentAuthor")).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBooksByAuthor("nonExistentAuthor"));
    }

    @Test
    void testGetBooksByAuthor_WhenEmptyResult() {
        when(bookRepo.findBooksByAuthor("emptyResult")).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBooksByAuthor("emptyResult"));
    }

    @Test
    void testGetBookByNameAndAuthor_BookExists() {
        String name = "name";
        String author = "author";
        Book mockBook = new Book(name, author, "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0);
        when(bookRepo.findBookByNameAndAuthor(name, author)).thenReturn(Optional.of(mockBook));

        Book result = bookServiceImpl.getBookByNameAndAuthor(name, author);

        assertEquals(mockBook, result);
    }

    @Test
    void testGetBookByNameAndAuthor_BookDoesNotExist() {
        String name = "nonExistentName";
        String author = "nonExistentAuthor";
        when(bookRepo.findBookByNameAndAuthor(name, author)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBookByNameAndAuthor(name, author));
    }

    @Test
    void testGetBooksByStudent_StudentExists() {
        long studentId = 1L;
        Student mockStudent = new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "gender", "password", "role", true);
        mockStudent.setId(studentId);

        List<Book> mockBookList = List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0));
        when(bookRepo.findBooksByStudentId(studentId)).thenReturn(mockBookList);

        List<Book> result = bookServiceImpl.getBooksByStudent(mockStudent);

        assertEquals(mockBookList, result);
    }

    @Test
    void testGetBooksByStudent_StudentDoesNotExist() {
        Student mockStudent = new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "gender", "password", "role", true);
        assertThrows(StudentNotFoundException.class, () -> bookServiceImpl.getBooksByStudent(mockStudent));
    }

    @Test
    void testGetBooksByCourse_CourseExists() {
        long courseId = 1L;
        Course mockCourse = new Course("name", "department", "instructor", 0);
        mockCourse.setId(courseId);

        List<Book> mockBookList = List.of(new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 15, 17).getTime(), "publisher", 0));
        when(bookRepo.findBooksByCourseId(courseId)).thenReturn(mockBookList);

        List<Book> result = bookServiceImpl.getBooksByCourse(mockCourse);

        assertEquals(mockBookList, result);
    }

    @Test
    void testGetBooksByCourse_CourseDoesNotExist() {
        Course mockCourse = new Course("name", "department", "instructor", 0);
        assertThrows(CourseNotFoundException.class, () -> bookServiceImpl.getBooksByCourse(mockCourse));
    }
}