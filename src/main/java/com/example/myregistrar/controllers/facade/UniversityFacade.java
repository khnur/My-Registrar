package com.example.myregistrar.controllers.facade;

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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniversityFacade {
    private final StudentService studentService;
    private final CourseService courseService;
    private final UniversityService universityService;

    public UniversityDto createUniversity(UniversityDto universityDto) {
        return UniversityMapper.INSTANCE.universityToUniversityDto(
                universityService.createUniversity(universityDto.toUniversity())
        );
    }

    public List<UniversityDto> getAllUniversities() {
        return UniversityMapper.INSTANCE
                .universityListToUniversityDtoList(universityService.getAllUniversities());
    }

    public UniversityDto getUniversityById(Long id) {
        return UniversityMapper.INSTANCE
                .universityToUniversityDto(universityService.getUniversityById(id));
    }


    public List<StudentDto> getStudentsByUniversity(Long id) {
        University university = universityService.getUniversityById(id);
        return StudentMapper.INSTANCE
                .studentListToStudentDtoList(studentService.getStudentsByUniversity(university));
    }

    public List<CourseDto> getCoursesByUniversity(Long id) {
        University university = universityService.getUniversityById(id);
        return CourseMapper.INSTANCE
                .courseListToCourseDtoList(courseService.getCoursesByUniversity(university));
    }
}
