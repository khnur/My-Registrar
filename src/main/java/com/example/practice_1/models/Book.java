package com.example.practice_1.models;

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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_seq"
    )
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long id;

    @Column(
            updatable = false,
            nullable = false
    )
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(
            name = "published_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id"
    )
    private Course course;

    public Book(String name, String author, LocalDate publishedDate) {
        this.name = name;
        this.author = author;
        this.publishedDate = publishedDate;
    }
}
