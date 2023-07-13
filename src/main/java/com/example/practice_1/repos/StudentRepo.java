package com.example.practice_1.repos;

import com.example.practice_1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.email = ?1")
    Optional<Student> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.email = ?1")
    boolean existsByEmail(String email);

    List<Student> findStudentsByFirstName(String firstName);

    List<Student> findStudentsByLastName(String lastName);

    Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);
}
