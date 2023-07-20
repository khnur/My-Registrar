package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Student;
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
    private List<Student> students = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public CourseDto(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name.trim();
        this.university = university.trim();
        this.department = department.trim();
        this.instructor = instructor.trim();
        this.creditHours = creditHours;
    }
}
