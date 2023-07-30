package com.example.myregistrar.dtos;

import com.example.myregistrar.util.JsonMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    @NotBlank
    private String name;
    private String department;
    private String instructor;
    private Integer creditHours;

    private UniversityDto university;

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }
}
