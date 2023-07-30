package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentFacade studentFacade;

    @PostMapping
    public StudentDto createStudent(@RequestBody StudentDto studentDto) {
        return studentFacade.createStudent(studentDto);
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentFacade.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentFacade.getStudentById(id);
    }

    @GetMapping("/{id}/report")
    public StudentReportDto getStudentReportById(@PathVariable Long id) {
        return studentFacade.getStudentReportById(id);
    }

    @GetMapping("/{id}/uni")
    public UniversityDto getUniversityByStudentId(@PathVariable Long id) {
        return studentFacade.getUniversityByStudentId(id);
    }

    @PutMapping("/{id}/uni")
    public StudentDto assignStudentToUniversity(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        return studentFacade.assignStudentToUniversity(id, universityDto);
    }

    @GetMapping("/{id}/course")
    public List<CourseDto> getCourseByStudentId(@PathVariable Long id) {
        return studentFacade.getCourseByStudentId(id);
    }

    @GetMapping("/{id}/book")
    public List<BookDto> getBooksByStudentId(@PathVariable Long id) {
        return studentFacade.getBooksByStudentId(id);
    }
}
