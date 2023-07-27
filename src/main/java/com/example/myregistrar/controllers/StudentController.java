package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public StudentDto createStudent(@RequestBody StudentDto studentDto) {
        return StudentMapper.INSTANCE.studentToStudentDto(
                studentService.createStudent(studentDto.toStudent())
        );
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return StudentMapper.INSTANCE
                .studentToStudentDto(studentService.getStudentById(id));
    }

    @GetMapping("/uni")
    public UniversityDto getUniversityByStudent(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName
    ) {
        Student student = studentService.getStudentByFirstNameAndLastName(firstName, lastName);
        if (student.getUniversity() == null) {
            throw new UniversityNotFoundException("Student with name=" + student.getFirstName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(student.getUniversity());
    }
}
