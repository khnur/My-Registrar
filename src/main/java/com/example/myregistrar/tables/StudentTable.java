package com.example.myregistrar.tables;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Registration;
import com.example.myregistrar.models.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentTable extends AbstractTable<Student> {
    protected StudentTable() {
        super(Student.class);
    }

    public boolean existsStudentByFirstNameAndLastName(String firstName, String lastName) {
        return super.existsByFirstAndSecond(firstName, lastName);
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
        super.save(student, student.getFirstName(), student.getLastName());
    }
}
