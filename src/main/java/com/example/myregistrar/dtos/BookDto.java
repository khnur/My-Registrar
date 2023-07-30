package com.example.myregistrar.dtos;

import com.example.myregistrar.util.JsonMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDto {
    private Long id;
    @NotBlank
    private String name;
    private String author;
    private String genre;
    private LocalDate publishedDate;
    private String publisher;
    private Integer pageNumber;

    private CourseDto course;

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

}
