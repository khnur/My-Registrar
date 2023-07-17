package com.example.practice_1.models;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Scanner;

import static com.example.practice_1.util.DateMapper.*;

@Data
@NoArgsConstructor
public class Book {
    private long id;

    private String name;

    private String author;

    private String genre;

    private Date publishedDate;

    private String publisher;

    private Course course;

    public Book(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
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
            System.out.print("Published Date (" + PATTERN + "): ");
            try {
                publishedDate = DATE_FORMAT.parse(scanner.next());
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
