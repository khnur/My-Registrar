package com.example.practice_1.tables;

import com.example.practice_1.models.Course;
import com.example.practice_1.models.Registration;
import com.example.practice_1.models.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentTable extends AbstractTable<Student> {
    public boolean existsStudentByFirstNameAndLastName(String firstName, String lastName) {
        return super.existsByFistAndSecond(firstName, lastName);
    }

    public List<Student> findStudentsByFirstName(String firstName) {
        return super.findListByFirst(firstName);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        return super.findListBySecond(lastName);
    }

    public Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName) {
        return super.findByFirstAndSecond(firstName, lastName);
    }

    public List<Student> findStudentsByCourse(Course course) {
        return course.getRegistrations().stream()
                .map(Registration::getStudent)
                .toList();
    }

    public void save(Student student) {
        map.put(toHash(student.getFirstName(), student.getLastName()), student);
    }
}
