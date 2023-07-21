package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.exceptions.BookAlreadyExistsException;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;

    @Transactional
    @Override
    public void createBook(Book book) {
        if (bookRepo.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new BookAlreadyExistsException("Book with such name and author already exists");
        }
        bookRepo.save(book);
    }

    @Override
    public void createBook(BookDto bookDto) {
        Book book = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        createBook(book);
    }

    @Override
    public void createRandomBooks(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createBook(Book.createRandomBook());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepo.findAll();
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book");
        }
        return bookList;
    }

    @Override
    public List<Book> getBooksByName(String name) {
        List<Book> bookList = bookRepo.findBooksByName(name);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book with such name");
        }
        return bookList;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> bookList = bookRepo.findBooksByAuthor(author);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book with such author");
        }
        return bookList;
    }

    @Override
    public Book getBookByNameAndAuthor(String name, String author) {
        return bookRepo.findBookByNameAndAuthor(name, author)
                .orElseThrow(() -> new BookNotFoundException("There is no book with such name and author"));
    }

    @Override
    public List<Book> getBooksByStudent(Student student) {
        List<Course> courses = student.getCourses();
        if (courses.isEmpty()) {
            throw new BookNotFoundException("The student does not have any book");
        }
        return bookRepo.findBooksByCourseIn(courses);
    }

    @Override
    public List<Book> getBooksByCourse(Course course) {
        List<Book> booksByCourse = bookRepo.findBooksByCourse(course);
        if (booksByCourse.isEmpty()) {
            throw new BookNotFoundException("There is no book in this course");
        }
        return booksByCourse;
    }
}