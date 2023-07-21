package com.example.myregistrar.dtos;

import com.example.myregistrar.models.CoursePreRequisite;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseDto {
    private Long id;

    private String name;

    private String university;

    private String department;

    private String instructor;

    private Integer creditHours;

    @ToString.Exclude
    @JsonIgnore
    private List<StudentDto> studentDtoList = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    private List<BookDto> bookDtoList = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    private List<CoursePreRequisite> coursePreRequisiteList = new ArrayList<>();

    public CourseDto(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.university = university;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }
}
