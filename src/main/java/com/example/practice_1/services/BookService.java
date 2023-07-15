package com.example.practice_1.services;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Registration;
import com.example.practice_1.models.Student;
import com.example.practice_1.repos.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    public Book createBook(Book book) throws RuntimeException {
        if (bookRepo.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new RuntimeException("Book with such name and author already exists");
        }
        return bookRepo.save(book);
    }

    public void createRandomBooks(int n) {
        for (int i = 0; i < n; ) {
            try {
                createBook(Book.createRandomBook());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public List<Book> getAllBooks() throws RuntimeException {
        List<Book> bookList = bookRepo.findAll();
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book");
        }
        return bookList;
    }

    public List<Book> getBooksByName(String name) throws RuntimeException {
        List<Book> bookList = bookRepo.findBooksByName(name);
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book with such name");
        }
        return bookList;
    }

    public List<Book> getBooksByAuthor(String author) throws RuntimeException {
        List<Book> bookList = bookRepo.findBooksByAuthor(author);
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book with such author");
        }
        return bookList;
    }

    public Book getBookByNameAndAuthor(String name, String author) throws RuntimeException {
        return bookRepo.findBookByNameAndAuthor(name, author)
                .orElseThrow(() -> new RuntimeException("There is no book with such name and author"));
    }

    public List<Book> getBooksByStudent(Student student) {
        List<Course> courses = student.getRegistrations().stream()
                .map(Registration::getCourse)
                .collect(Collectors.toList());
        if (courses.isEmpty()) {
            throw new RuntimeException("The student does not have any book");
        }
        return bookRepo.findBooksByCourseIn(courses);
    }

    public List<Book> getBooksByCourse(Course course) {
        List<Book> booksByCourse = bookRepo.findBooksByCourse(course);
        if (booksByCourse.isEmpty()) {
            throw new RuntimeException("There is no book in this course");
        }
        return booksByCourse;
    }


}
