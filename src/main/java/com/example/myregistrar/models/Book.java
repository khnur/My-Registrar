package com.example.myregistrar.models;

import com.example.myregistrar.util.ConsoleInput;
import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@Slf4j
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "course_book_fk")
    )
    @ToString.Exclude
    @JsonIgnore
    private Course course;

    public Book(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

    public static Book getInstance() throws IOException {
        log.info("Name: ");
        String name = ConsoleInput.readLine();

        log.info("Author: ");
        String author = ConsoleInput.readLine();

        log.info("Genre: ");
        String genre = ConsoleInput.readLine();

        Date publishedDate = null;
        while (publishedDate == null) {
            log.info("Published Date (" + DateMapper.PATTERN + "): ");
            try {
                publishedDate = DateMapper.DATE_FORMAT.parse(ConsoleInput.readLine());
            } catch (Exception e) {
                log.error("Entered incorrect for of date. Try again\n");
            }
        }

        log.info("Publisher: ");
        String publisher = ConsoleInput.readLine();

        return new Book(name, author, genre, publishedDate, publisher);

    }
    public static Book createRandomBook() {
        Faker faker = Faker.instance();
        com.github.javafaker.Book book = faker.book();

        String name = book.title();
        String author = book.author();
        String genre = book.genre();
        Date publishedDate = faker.date().birthday();
        String publisher = book.publisher();

        return new Book(name, author, genre, publishedDate, publisher);
    }
}
