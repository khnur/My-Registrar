package com.example.myregistrar.models;

import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Scanner;

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
        this.name = name.trim();
        this.author = author.trim();
        this.genre = genre.trim();
        this.publishedDate = publishedDate;
        this.publisher = publisher.trim();
    }

    public static Book getInstance(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Author: ");
        String author = scanner.next();

        System.out.print("Genre: ");
        String genre = scanner.next();

        Date publishedDate = null;
        while (publishedDate == null) {
            System.out.print("Published Date (" + DateMapper.PATTERN + "): ");
            try {
                publishedDate = DateMapper.DATE_FORMAT.parse(scanner.next().trim());
            } catch (Exception e) {
                System.out.println("Entered incorrect for of date. Try again\n");
            }
        }

        System.out.print("Publisher: ");
        String publisher = scanner.next();

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
