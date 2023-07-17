package com.example.myregistrar.tables;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookTable extends AbstractTable<Book> {
    public BookTable(JdbcTemplate jdbcTemplate) {
        super(Book.class, jdbcTemplate);
    }

    public boolean existsByNameAndAuthor(String name, String author) {
        return super.existsByFirstAndSecond(name, author);
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
                .toList();
    }

    public void save(Book book) {
        super.save(book, book.getName(), book.getAuthor());
    }
}
