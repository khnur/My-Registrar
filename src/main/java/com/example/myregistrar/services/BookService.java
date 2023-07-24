package com.example.myregistrar.services;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;

import java.util.List;

public interface BookService {
    void createBook(Book book);

    void generateRandomBooks(int n);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByName(String name);

    List<Book> getBooksByAuthor(String author);

    Book getBookByNameAndAuthor(String name, String author);

    List<Book> getBooksByStudent(Student student);

    List<Book> getBooksByCourse(Course course);
}
