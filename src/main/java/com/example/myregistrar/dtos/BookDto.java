package com.example.myregistrar.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private String author;
    private String genre;
    private Date publishedDate;
    private String publisher;
    @ToString.Exclude
    @JsonIgnore
    private CourseDto courseDto;

    public BookDto(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }
}
