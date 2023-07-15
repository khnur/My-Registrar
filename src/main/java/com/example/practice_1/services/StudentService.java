package com.example.practice_1.services;

import com.example.practice_1.models.Student;
import com.example.practice_1.repos.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;

    public Student createStudent(Student student) throws RuntimeException {
        if (studentRepo.existsStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName())) {
            throw new RuntimeException("Student with such email already exists");
        }
        return studentRepo.save(student);
    }

    public void createRandomStudents(int n) {
        for (int i = 0; i < n; ) {
            try {
                createStudent(Student.createRandomStudent());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public List<Student> getAllStudents() throws RuntimeException {
        List<Student> studentList = studentRepo.findAll();
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student");
        }
        return studentList;
    }

    public List<Student> getStudentsByFirstName(String firstName) throws RuntimeException {
        List<Student> studentList = studentRepo.findStudentsByFirstName(firstName);
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student with such first name");
        }
        return studentList;
    }

    public List<Student> getStudentsByLastName(String lastName) throws RuntimeException {
        List<Student> studentList = studentRepo.findStudentsByLastName(lastName);
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student with such last name");
        }
        return studentList;
    }

    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) throws RuntimeException {
        return studentRepo.findStudentByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("There is no student with such first and last name"));
    }
}
