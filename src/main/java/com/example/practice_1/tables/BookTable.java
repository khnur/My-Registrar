package com.example.practice_1.tables;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookTable extends AbstractTable<Book> {
    public boolean existsByNameAndAuthor(String name, String author) {
        return super.existsByFistAndSecond(name, author);
    }

    public List<Book> findBooksByName(String name) {
        return super.findListByFirst(name);
    }

    public List<Book> findBooksByAuthor(String author) {
        return super.findListBySecond(author);
    }

    public Optional<Book> findBookByNameAndAuthor(String name, String author) {
        return super.findByFirstAndSecond(name, author);
    }

    public List<Book> findBooksByCourse(Course course) {
        return course.getBooks();
    }

    public List<Book> findBooksByCourseIn(List<Course> courses) {
        return courses.stream()
                .flatMap(course -> course.getBooks().stream())
                .collect(Collectors.toList());
    }

    public void save(Book book) {
        map.put(toHash(book.getName(), book.getAuthor()), book);
    }
}
