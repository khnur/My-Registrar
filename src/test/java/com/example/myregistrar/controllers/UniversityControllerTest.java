package com.example.myregistrar.controllers;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class UniversityControllerTest {
    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    UniversityService universityService;
    @InjectMocks
    UniversityController universityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUniversity() {
        University university = new University("name", "country", "city");
        when(universityService.createUniversity(any())).thenReturn(university);

        UniversityDto result = universityController.createUniversity(UniversityMapper.INSTANCE.universityToUniversityDto(university));
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
        when(universityService.getAllUniversities()).thenReturn(List.of(new University("name", "country", "city")));

        List<UniversityDto> result = universityController.getAllUniversities();
        result.get(0).setId(1L);
        Assertions.assertEquals(List.of(new UniversityDto(Long.valueOf(1), "name", "country", "city")), result);
    }

    @Test
    void testGetUniversityById_UniversityNotFound() {
        when(universityService.getUniversityById(anyLong())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> universityController.getUniversityById(1L));
    }

    @Test
    void testGetUniversityById() {
        when(universityService.getUniversityById(anyLong())).thenReturn(new University("name", "country", "city"));

        UniversityDto result = universityController.getUniversityById(1L);
        result.setId(1L);

        Assertions.assertEquals(new UniversityDto(1L, "name", "country", "city"), result);
    }

    @Test
    void testGetStudentsByUniversity() {
        List<Student> students = List.of(new Student("firstName", "lastName",
                new GregorianCalendar(2023, Calendar.JULY, 28, 22, 47).getTime(),
                "gender", "password", "role"));
        when(studentService.getStudentsByUniversity(any())).thenReturn(
                students);
        when(universityService.getUniversityById(anyLong())).thenReturn(new University("name", "country", "city"));

        List<StudentDto> result = universityController.getStudentsByUniversity(1L);
        Assertions.assertEquals(StudentMapper.INSTANCE.studentListToStudentDtoList(students), result);
    }

    @Test
    void testGetCoursesByUniversity() {
        List<Course> courseList = List.of(new Course("name", "department", "instructor", 0));
        when(courseService.getCoursesByUniversity(any())).thenReturn(courseList);
        when(universityService.getUniversityById(anyLong())).thenReturn(new University("name", "country", "city"));

        List<CourseDto> result = universityController.getCoursesByUniversity(1L);
        Assertions.assertEquals(CourseMapper.INSTANCE.courseListToCourseDtoList(courseList), result);
    }
}