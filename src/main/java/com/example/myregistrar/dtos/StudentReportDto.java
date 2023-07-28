package com.example.myregistrar.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportDto {
    private String name;
    private String email;
    private Integer numberOfCoursesCompleted;
    private Integer numberOfCoursesTaking;
    private Integer totalCreditHours;
    private Integer pageNumberRead;
    private Integer pageNumberUpToRead;

    private List<CourseDto> courses;
    private UniversityDto university;
}
