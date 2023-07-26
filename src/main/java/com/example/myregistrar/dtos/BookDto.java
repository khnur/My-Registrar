package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
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
    private String courseName;

    @ToString.Exclude
    @JsonIgnore
    private Course course;

    public Book toBook() {
        return BookMapper.INSTANCE.bookDtoToBook(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public void setCourse(Course course) {
        this.course = course;
        this.courseName = course != null ? this.course.getName() : null;
    }
}
