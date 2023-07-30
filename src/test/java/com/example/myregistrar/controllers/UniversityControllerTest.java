package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class UniversityControllerTest {
    @Mock
    StudentFacade studentService;
    @Mock
    CourseFacade courseService;
    @Mock
    UniversityFacade universityService;
    @InjectMocks
    UniversityController universityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUniversity() {
        University university = new University("name", "country", "city");
        when(universityService.createUniversity(any())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        UniversityDto result = universityController
                .createUniversity(UniversityMapper.INSTANCE.universityToUniversityDto(university))
                .getObject();

        result.setId(1L);
        Assertions.assertEquals(new UniversityDto(1L, "name", "country", "city"), result);
    }
    @Test
    void testCreateUniversity_InvalidData() {
        when(universityService.createUniversity(any())).thenThrow(new UniversityNotFoundException("Invalid university data"));
        Assertions.assertThrows(UniversityNotFoundException.class, () -> universityController.createUniversity(new UniversityDto()));
    }

    @Test
    void testGetAllUniversities() {
        University university = new University("name", "country", "city");
        when(universityService.getAllUniversities()).thenReturn(List.of(UniversityMapper.INSTANCE.universityToUniversityDto(university)));

        List<UniversityDto> result = universityController.getAllUniversities();
        result.get(0).setId(1L);
        Assertions.assertEquals(List.of(new UniversityDto(1L, "name", "country", "city")), result);
    }

    @Test
    void testGetUniversityById_UniversityNotFound() {
        when(universityService.getUniversityById(anyLong())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> universityController.getUniversityById(1L));
    }

    @Test
    void testGetUniversityById() {
        University university = new University("name", "country", "city");
        when(universityService.getUniversityById(anyLong())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        UniversityDto result = universityController.getUniversityById(1L);
        result.setId(1L);

        Assertions.assertEquals(new UniversityDto(1L, "name", "country", "city"), result);
    }

    @Test
    void testGetStudentsByUniversity() {
        List<Student> students = List.of(new Student("firstName", "lastName",
                LocalDate.EPOCH, "male"));
        when(studentService.getStudentsByUniversity(any())).thenReturn(StudentMapper.INSTANCE.studentListToStudentDtoList(students));

        University university = new University("name", "country", "city");
        when(universityService.getUniversityById(anyLong())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        List<StudentDto> result = universityController.getStudentsByUniversity(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetCoursesByUniversity() {
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getCoursesByUniversity(any())).thenReturn(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList));

        University university = new University("name", "country", "city");
        when(universityService.getUniversityById(anyLong())).thenReturn(UniversityMapper.INSTANCE.universityToUniversityDto(university));

        List<CourseDto> result = universityController.getCoursesByUniversity(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }
}