package com.example.myregistrar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate publishedDate;

    @Column
    private String publisher;

    @Column(name = "page_number")
    private Integer pageNumber;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "course_book_fk")
    )
    private Course course;

    public Book(String name, String author, String genre, LocalDate publishedDate, String publisher, Integer pageNUmber) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.pageNumber = pageNUmber;
    }
}
