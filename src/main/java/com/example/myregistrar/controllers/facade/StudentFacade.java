package com.example.myregistrar.controllers.facade;

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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentFacade {
    private final StudentService studentService;
    private final CourseService courseService;
    private final BookService bookService;
    private final UniversityService universityService;

    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.INSTANCE.studentDtoToStudent(studentDto);
        return StudentMapper.INSTANCE.studentToStudentDto(
                studentService.createStudent(student)
        );
    }

    public List<StudentDto> getAllStudents() {
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getAllStudents());
    }

    public StudentDto getStudentById(Long id) {
        return StudentMapper.INSTANCE
                .studentToStudentDto(studentService.getStudentById(id));
    }

    public StudentReportDto getStudentReportById(Long id) {
        Student student = studentService.getStudentById(id);
        return studentService.getStudentReport(student);
    }

    public UniversityDto getUniversityByStudentId(Long id) {
        Student student = studentService.getStudentById(id);
        if (student.getUniversity() == null) {
            throw new UniversityNotFoundException("Student with name=" + student.getFirstName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(student.getUniversity());
    }

    public StudentDto assignStudentToUniversity(Long id, UniversityDto universityDto) {
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

    public List<CourseDto> getCourseByStudentId(Long id) {
        Student student = studentService.getStudentById(id);
        return CourseMapper.INSTANCE.courseListToCourseDtoList(
                courseService.getCoursesByStudent(student)
        );
    }

    public List<BookDto> getBooksByStudentId(Long id) {
        Student student = studentService.getStudentById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByStudent(student)
        );
    }
}
