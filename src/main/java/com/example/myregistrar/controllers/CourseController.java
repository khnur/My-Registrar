package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.exceptions.CourseNotFoundException;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
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
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final BookService bookService;
    private final UniversityService universityService;

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        return CourseMapper.INSTANCE.courseToCourseDto(
                courseService.createCourse(courseDto.toCourse())
        );
    }

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return CourseMapper.INSTANCE
                .courseListToCourseDtoList(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return CourseMapper.INSTANCE
                .courseToCourseDto(courseService.getCourseById(id));
    }

    @GetMapping("/{id}/student")
    public List<StudentDto> getStudentsByCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return StudentMapper.INSTANCE.studentListToStudentDtoList(
                studentService.getStudentsByCourse(course)
        );
    }

    @GetMapping("/{id}/uni")
    public UniversityDto getUniversityByCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course.getUniversity() == null) {
            throw new UniversityNotFoundException("Course with name=" + course.getName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(
                universityService.getUniversityById(course.getUniversity().getId()));
    }

    @PutMapping("/{id}/uni")
    public CourseDto assignUniversityToCourse(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        if (universityDto == null || universityDto.getId() == null) {
            throw new UniversityNotFoundException("Provided transient university and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        University university;
        if (universityDto.getId() != null) {
            university = universityService.getUniversityById(universityDto.getId());
        } else {
            university = universityService.getUniversityByNameAndCountry(universityDto.getName(), universityDto.getCountry());
        }

        courseService.assignUniversityToCourse(course, university);

        return CourseMapper.INSTANCE.courseToCourseDto(courseService.getCourseById(id));
    }

    @GetMapping("/{id}/book")
    public List<BookDto> getBooksByCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByCourse(course)
        );
    }

    @PutMapping("/{id}/book")
    public BookDto assignBookToCourse(@PathVariable Long id, @RequestBody BookDto bookDto) {
        if (bookDto == null || bookDto.getId() == null) {
            throw new BookNotFoundException("Provided transient book and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        Book book = bookService.getBookById(bookDto.getId());

        courseService.assignBookToCourse(course, book);

        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    @GetMapping("/{id}/prereq")
    public List<CourseDto> getPreReqsByCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return CourseMapper.INSTANCE.courseListToCourseDtoList(
                courseService.getCoursePreRequisitesFromCourse(course)
        );
    }

    @PutMapping("/{id}/prereq")
    public CourseDto assignPreReqFromCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        if (courseDto == null || courseDto.getId() == null) {
            throw new CourseNotFoundException("Provided transient course and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        Course coursePreReq = courseService.getCourseById(courseDto.getId());

        courseService.assignCoursePreRequisiteCourse(course, coursePreReq);

        return CourseMapper.INSTANCE.courseToCourseDto(courseService.getCourseById(id));
    }

    @GetMapping("/{id}/notify")
    public List<StudentDto> getNotifiedStudents(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        courseService.notifyStudentsWithinUniversity(course);

        return StudentMapper.INSTANCE.studentListToStudentDtoList(
                studentService.getStudentsByCourse(course)
        );
    }
}
