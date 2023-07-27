package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import lombok.Data;

import java.util.Date;

@Data
public class BookDto {
    private Long id;
    private String name;
    private String author;
    private String genre;
    private Date publishedDate;
    private String publisher;

    private CourseDto course;

    public Book toBook() {
        return BookMapper.INSTANCE.bookDtoToBook(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

}
