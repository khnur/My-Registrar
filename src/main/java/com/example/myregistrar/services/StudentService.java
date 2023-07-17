package com.example.myregistrar.services;

import com.example.myregistrar.tables.CourseTable;
import com.example.myregistrar.tables.StudentTable;
import com.example.myregistrar.exceptions.StudentAlreadyExistsException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.tables.BookTable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class StudentService extends AbstractService {
    public StudentService(StudentTable studentData, CourseTable courseData, BookTable bookData) {
        super(studentData, courseData, bookData);
    }

    public void createStudent(Student student) {
        if (studentData.existsStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName())) {
            throw new StudentAlreadyExistsException("Student with such name and last name already exists");
        }
        studentData.save(student);
    }

    public void createRandomStudents(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createStudent(Student.createRandomStudent());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = studentData.findAll();
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student");
        }
        return studentList;
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        List<Student> studentList = studentData.findStudentsByFirstName(firstName.trim());
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such first name");
        }
        return studentList;
    }

    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> studentList = studentData.findStudentsByLastName(lastName.trim());
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such last name");
        }
        return studentList;
    }

    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) {
        return studentData.findStudentByFirstNameAndLastName(firstName.trim(), lastName.trim())
                .orElseThrow(() -> new StudentNotFoundException("There is no student with such first and last name"));
    }
}
