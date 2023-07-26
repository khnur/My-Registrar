package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.University;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
public class CourseDto {
    private Long id;

    private String name;

    private String universityName;

    private String department;

    private String instructor;

    private Integer creditHours;

    @ToString.Exclude
    @JsonIgnore
    private University university;

    public Course toCourse() {
        return CourseMapper.INSTANCE.courseDtoToCourse(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public void setUniversity(University university) {
        this.university = university;
        this.universityName = university != null ? university.getName() : null;
    }
}
