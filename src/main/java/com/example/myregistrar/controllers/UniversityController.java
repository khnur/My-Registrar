package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityFacade universityFacade;
    private final StudentFacade studentFacade;
    private final CourseFacade courseFacade;

    @PostMapping
    public ResponseDto<UniversityDto> createUniversity(@RequestBody @Valid UniversityDto universityDto) {
        UniversityDto newUniversityDto = universityFacade.createUniversity(universityDto);
        if (newUniversityDto == null) {
            return new ResponseDto<>(
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
        }
        return new ResponseDto<>(
                OK.value(),
                OK.getReasonPhrase(),
                newUniversityDto
        );
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
