package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityFacade universityFacade;
    private final StudentFacade studentFacade;
    private final CourseFacade courseFacade;

    @PostMapping
    public UniversityDto createUniversity(@RequestBody UniversityDto universityDto) {
        return universityFacade.createUniversity(universityDto);
    }

    @GetMapping
    public List<UniversityDto> getAllUniversities() {
        return universityFacade.getAllUniversities();
    }

    @GetMapping("/{id}")
    public UniversityDto getUniversityById(@PathVariable Long id) {
        return universityFacade.getUniversityById(id);
    }


    @GetMapping("/{id}/student")
    public List<StudentDto> getStudentsByUniversity(@PathVariable Long id) {
        return studentFacade.getStudentsByUniversity(id);
    }

    @GetMapping("/{id}/course")
    public List<CourseDto> getCoursesByUniversity(@PathVariable Long id) {
        return courseFacade.getCoursesByUniversity(id);
    }
}
