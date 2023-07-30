package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseFacade courseFacade;
    private final BookFacade bookFacade;
    private final StudentFacade studentFacade;
    private final UniversityFacade universityFacade;

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        return courseFacade.createCourse(courseDto);
    }

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseFacade.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return courseFacade.getCourseById(id);
    }

    @GetMapping("/{id}/student")
    public List<StudentDto> getStudentsByCourse(@PathVariable Long id) {
        return studentFacade.getStudentsByCourse(id);
    }

    @GetMapping("/{id}/uni")
    public UniversityDto getUniversityByCourse(@PathVariable Long id) {
        return universityFacade.getUniversityByCourse(id);
    }

    @PutMapping("/{id}/uni")
    public CourseDto assignUniversityToCourse(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        return courseFacade.assignUniversityToCourse(id, universityDto);
    }

    @GetMapping("/{id}/book")
    public List<BookDto> getBooksByCourse(@PathVariable Long id) {
        return bookFacade.getBooksByCourse(id);
    }

    @PutMapping("/{id}/book")
    public BookDto assignBookToCourse(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookFacade.assignBookToCourse(id, bookDto);
    }

    @GetMapping("/{id}/prereq")
    public List<CourseDto> getPreReqsByCourse(@PathVariable Long id) {
        return courseFacade.getPreReqsByCourse(id);
    }

    @PutMapping("/{id}/prereq")
    public CourseDto assignPreReqFromCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        return courseFacade.assignPreReqFromCourse(id, courseDto);
    }

    @GetMapping("/{id}/notify")
    public List<StudentDto> getNotifiedStudents(@PathVariable Long id) {
        return studentFacade.getNotifiedStudents(id);
    }
}
