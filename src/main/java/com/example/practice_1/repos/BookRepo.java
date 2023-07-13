package com.example.practice_1.repos;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);

    List<Book> findBooksByName(String name);

    List<Book> findBooksByAuthor(String author);

    Optional<Book> findBookByNameAndAuthor(String name, String author);
}
