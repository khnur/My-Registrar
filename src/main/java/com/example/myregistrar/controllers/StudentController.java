package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.*;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
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
    private final CourseService courseService;
    private final BookService bookService;
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

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return StudentMapper.INSTANCE
                .studentToStudentDto(studentService.getStudentById(id));
    }

    @GetMapping("/{id}/report")
    public StudentReportDto getStudentReportById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return studentService.getStudentReport(student);
    }

    @GetMapping("/{id}/uni")
    public UniversityDto getUniversityByStudentId(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student.getUniversity() == null) {
            throw new UniversityNotFoundException("Student with name=" + student.getFirstName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(student.getUniversity());
    }

    @PutMapping("/{id}/uni")
    public StudentDto assignStudentToUniversity(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        if (universityDto == null || universityDto.getId() == null) {
            throw new UniversityNotFoundException("Provided transient university and it is not registered");
        }

        Student student = studentService.getStudentById(id);
        University university;
        if (universityDto.getId() != null) {
            university = universityService.getUniversityById(universityDto.getId());
        } else {
            university = universityService.getUniversityByNameAndCountry(universityDto.getName(), universityDto.getCountry());
        }

        studentService.assignUniversityToStudent(student, university);

        return StudentMapper.INSTANCE.studentToStudentDto(studentService.getStudentById(id));
    }

    @GetMapping("/{id}/course")
    public List<CourseDto> getCourseByStudentId(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return CourseMapper.INSTANCE.courseListToCourseDtoList(
                courseService.getCoursesByStudent(student)
        );
    }

    @GetMapping("/{id}/book")
    public List<BookDto> getBooksByStudentId(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByStudent(student)
        );
    }
}
