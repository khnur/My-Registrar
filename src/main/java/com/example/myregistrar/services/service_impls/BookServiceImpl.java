package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.exceptions.BookAlreadyExistsException;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.BookRepo;
import com.example.myregistrar.repositories.CourseRepo;
import com.example.myregistrar.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final CourseRepo courseRepo;

    @Transactional
    @Override
    public void createBook(Book book) {
        if (book == null) {
            log.error("The book is null");
            return;
        }
        if (bookRepo.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new BookAlreadyExistsException("Book with such name and author already exists");
        }
        bookRepo.save(book);
    }

    @Override
    public void generateRandomBooks(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createBook(BookDto.createRandomBookDto().toBook());
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
        if (student == null) {
            log.error("The student is null");
            throw new StudentNotFoundException("The student is null");
        }

        return bookRepo.findBooksByStudentId(student.getId());
    }

    @Override
    public List<Book> getBooksByCourse(Course course) {
        if (course == null) {
            log.error("The course is null");
            throw new CourseNotFoundException("The course is null");
        }

        List<Book> booksByCourse = bookRepo.findBooksByCourseId(course.getId());
        if (booksByCourse.isEmpty()) {
            throw new BookNotFoundException("There is no book in this course");
        }
        return booksByCourse;
    }
}
