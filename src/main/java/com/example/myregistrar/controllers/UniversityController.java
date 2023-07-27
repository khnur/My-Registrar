package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final UniversityService universityService;

    @GetMapping
    public List<UniversityDto> getAllUniversities() {
        return UniversityMapper.INSTANCE
                .universityListToUniversityDtoList(universityService.getAllUniversities());
    }

    @GetMapping("/{id}")
    public UniversityDto getUniversityById(@PathVariable Long id) {
        return UniversityMapper.INSTANCE
                .universityToUniversityDto(universityService.getUniversityById(id));
    }


    @GetMapping("/students")
    public List<StudentDto> getStudentsByUniversity(
            @RequestParam("universityName") String universityName,
            @RequestParam("country") String country
    ) {
        University university = universityService.getUniversityByNameAndCountry(universityName, country);
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getStudentsByUniversity(university));
    }

    @GetMapping("/courses")
    public List<CourseDto> getCoursesByUniversity(
            @RequestParam("universityName") String universityName,
            @RequestParam("country") String country
    ) {
        University university = universityService.getUniversityByNameAndCountry(universityName, country);
        return CourseMapper.INSTANCE
                .courseListToCourseDtoList(courseService.getCoursesByUniversity(university));
    }
}
