package com.example.myregistrar.controllers;

import com.example.myregistrar.annotation.LogDuration;
import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.*;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@LogDuration
public class StudentController {
    private final StudentFacade studentFacade;
    private final CourseFacade courseFacade;
    private final UniversityFacade universityFacade;
    private final BookFacade bookFacade;

    @PostMapping
    public ResponseDto<StudentDto> createStudent(@RequestBody @Valid StudentDto studentDto) {
        StudentDto newStudentDto = studentFacade.createStudent(studentDto);
        if (newStudentDto == null) {
            return new ResponseDto<>(
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
        }
        return new ResponseDto<>(
                OK.value(),
                OK.getReasonPhrase(),
                newStudentDto
        );
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
        return universityFacade.getUniversityByStudentId(id);
    }

    @PutMapping("/{id}/uni")
    public StudentDto assignStudentToUniversity(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        return studentFacade.assignStudentToUniversity(id, universityDto);
    }

    @GetMapping("/{id}/course")
    public List<CourseDto> getCourseByStudentId(@PathVariable Long id) {
        return courseFacade.getCourseByStudentId(id);
    }

    @GetMapping("/{id}/book")
    public List<BookDto> getBooksByStudentId(@PathVariable Long id) {
        return bookFacade.getBooksByStudentId(id);
    }
}
