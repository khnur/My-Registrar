package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class BookDto {
    private Long id;
    private String name;
    private String author;
    private String genre;
    private Date publishedDate;
    private String publisher;
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    public BookDto(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name.trim();
        this.author = author.trim();
        this.genre = genre.trim();
        this.publishedDate = publishedDate;
        this.publisher = publisher.trim();
    }
}
