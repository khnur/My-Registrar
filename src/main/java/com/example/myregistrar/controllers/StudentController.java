package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final UniversityService universityService;

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

    @GetMapping("/{firstName}")
    public StudentDto getStudentByFirstName(@PathVariable String firstName) {
        return studentService.getStudentsByFirstName(firstName).get(0).toStudentDto();
    }

    @GetMapping("/uni")
    public List<StudentDto> getStudentsByUniversity(
            @RequestParam("name") String universityName,
            @RequestParam("country") String country
    ) {
        University university = universityService.getUniversityByNameAndCountry(universityName, country);
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getStudentsByUniversity(university));
    }
}
