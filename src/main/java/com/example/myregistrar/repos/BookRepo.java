package com.example.myregistrar.repos;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String author);

    Optional<Book> findBookByNameAndAuthor(String name, String author);

    List<Book> findBooksByCourse(Course course);

    List<Book> findBooksByCourseIn(List<Course> courses);
}
