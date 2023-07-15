package com.example.practice_1.repos;

import com.example.practice_1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    boolean existsStudentByFirstNameAndLastName(String firstName, String lastName);

    List<Student> findStudentsByFirstName(String firstName);

    List<Student> findStudentsByLastName(String lastName);

    Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);
}
