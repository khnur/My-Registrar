package com.example.myregistrar.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String university;
    private String department;
    private String instructor;
    private Integer creditHours;
    private List<StudentDto> studentDtoList = new ArrayList<>();
    private List<BookDto> bookDtoList = new ArrayList<>();

    public CourseDto(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.university = university;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }
}
