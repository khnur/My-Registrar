package com.example.myregistrar.controllers.facade;

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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseFacade {
    private final StudentService studentService;
    private final CourseService courseService;
    private final BookService bookService;
    private final UniversityService universityService;

    public CourseDto createCourse(CourseDto courseDto) {
        Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);
        return CourseMapper.INSTANCE.courseToCourseDto(
                courseService.createCourse(course)
        );
    }

    public List<CourseDto> getAllCourses() {
        return CourseMapper.INSTANCE
                .courseListToCourseDtoList(courseService.getAllCourses());
    }

    public CourseDto getCourseById(Long id) {
        return CourseMapper.INSTANCE
                .courseToCourseDto(courseService.getCourseById(id));
    }

    public List<StudentDto> getStudentsByCourse(Long id) {
        Course course = courseService.getCourseById(id);
        return StudentMapper.INSTANCE.studentListToStudentDtoList(
                studentService.getStudentsByCourse(course)
        );
    }

    public UniversityDto getUniversityByCourse(Long id) {
        Course course = courseService.getCourseById(id);
        if (course.getUniversity() == null) {
            throw new UniversityNotFoundException("Course with name=" + course.getName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(
                universityService.getUniversityById(course.getUniversity().getId()));
    }

    public CourseDto assignUniversityToCourse(Long id, UniversityDto universityDto) {
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

    public List<BookDto> getBooksByCourse(Long id) {
        Course course = courseService.getCourseById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByCourse(course)
        );
    }

    public BookDto assignBookToCourse(Long id, BookDto bookDto) {
        if (bookDto == null || bookDto.getId() == null) {
            throw new BookNotFoundException("Provided transient book and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        Book book = bookService.getBookById(bookDto.getId());

        courseService.assignBookToCourse(course, book);

        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    public List<CourseDto> getPreReqsByCourse(Long id) {
        Course course = courseService.getCourseById(id);
        return CourseMapper.INSTANCE.courseListToCourseDtoList(
                courseService.getCoursePreRequisitesFromCourse(course)
        );
    }

    public CourseDto assignPreReqFromCourse(Long id, CourseDto courseDto) {
        if (courseDto == null || courseDto.getId() == null) {
            throw new CourseNotFoundException("Provided transient course and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        Course coursePreReq = courseService.getCourseById(courseDto.getId());

        courseService.assignCoursePreRequisiteCourse(course, coursePreReq);

        return CourseMapper.INSTANCE.courseToCourseDto(courseService.getCourseById(id));
    }

    public List<StudentDto> getNotifiedStudents(Long id) {
        Course course = courseService.getCourseById(id);
        University university = course.getUniversity();
        courseService.notifyStudentsWithinUniversity(course);

        return StudentMapper.INSTANCE.studentListToStudentDtoList(
                studentService.getStudentsByUniversity(university)
        );
    }
}
