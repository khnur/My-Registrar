package com.example.myregistrar.models;

import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Scanner;

@Data
@NoArgsConstructor
public class Book {
    private long id;

    private String name;

    private String author;

    private String genre;

    private Date publishedDate;

    private String publisher;

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
