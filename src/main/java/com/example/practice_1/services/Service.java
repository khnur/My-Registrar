package com.example.practice_1.services;

import com.example.practice_1.models.Student;
import com.example.practice_1.repos.BookRepo;
import com.example.practice_1.repos.CourseRepo;
import com.example.practice_1.repos.StudentRepo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final BookRepo bookRepo;

    public Student createStudent(Student student) throws RuntimeException{
        if (studentRepo.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with such email exists");
        }
        return studentRepo.save(student);
    }

    public void createRandomStudents(int n) {
        for (int i = 0; i < n;) {
            try {
                createStudent(Student.createRandomStudent());
                i++;
            } catch (Exception ignored) {}
        }
    }

}
