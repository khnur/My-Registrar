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

    @PostMapping
    public UniversityDto createUniversity(@RequestBody UniversityDto universityDto) {
        return UniversityMapper.INSTANCE.universityToUniversityDto(
                universityService.createUniversity(universityDto.toUniversity())
        );
    }

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


    @GetMapping("/{id}/student")
    public List<StudentDto> getStudentsByUniversity(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getStudentsByUniversity(university));
    }

    @GetMapping("/{id}/course")
    public List<CourseDto> getCoursesByUniversity(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);
        return CourseMapper.INSTANCE
                .courseListToCourseDtoList(courseService.getCoursesByUniversity(university));
    }
}
