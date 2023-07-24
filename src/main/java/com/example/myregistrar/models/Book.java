package com.example.myregistrar.models;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "book_index")
@Entity
@Table
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column
    private String genre;

    @Temporal(TemporalType.DATE)
    @Column(name = "published_date")
    private Date publishedDate;

    @Column
    private String publisher;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "course_book_fk")
    )
    private Course course;

    public Book(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

    public BookDto toBookDto() {
        return BookMapper.INSTANCE.bookToBookDto(this);
    }
}
