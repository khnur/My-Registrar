package com.example.myregistrar.controllers.facade;

import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.exceptions.not_found.UniversityNotFoundException;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public UniversityDto getUniversityByStudentId(Long id) {
        Student student = studentService.getStudentById(id);
        if (student.getUniversity() == null) {
            throw new UniversityNotFoundException("Student with name=" + student.getFirstName() +
                    " has not been approved by any university");
        }
        return UniversityMapper.INSTANCE.universityToUniversityDto(student.getUniversity());
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
}
