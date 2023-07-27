package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String department;
    private String instructor;
    private Integer creditHours;

    private UniversityDto university;

    public Course toCourse() {
        return CourseMapper.INSTANCE.courseDtoToCourse(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }
}
