package com.example.myregistrar.repositories;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);
    List<Book> findBooksByName(String name);
    List<Book> findBooksByAuthor(String author);
    Optional<Book> findBookByNameAndAuthor(String name, String author);

    List<Book> findBooksByCourseId(Long courseId);
    @Query("SELECT b FROM Book b JOIN b.course c JOIN c.students s WHERE s.id = :studentId")
    List<Book> findBooksByStudentId(@Param("studentId") Long studentId);

}
