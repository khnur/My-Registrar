package com.example.myregistrar.services;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.exceptions.StudentAlreadyExistsException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;

    @Transactional
    public void createStudent(Student student) {
        if (studentRepo.existsStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName())) {
            throw new StudentAlreadyExistsException("Student with such name and last name already exists");
        }
        studentRepo.save(student);
    }

    @Transactional
    public void createStudent(StudentDto studentDto) {
        Student student = StudentMapper.INSTANCE.studentDtoToStudent(studentDto);
        createStudent(student);
    }

    @Transactional
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
        List<Student> studentList = studentRepo.findAll();
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student");
        }
        return studentList;
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        List<Student> studentList = studentRepo.findStudentsByFirstName(firstName.trim());
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such first name");
        }
        return studentList;
    }

    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> studentList = studentRepo.findStudentsByLastName(lastName.trim());
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("There is no student with such last name");
        }
        return studentList;
    }

    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepo.findStudentByFirstNameAndLastName(firstName.trim(), lastName.trim())
                .orElseThrow(() -> new StudentNotFoundException("There is no student with such first and last name"));
    }

}
