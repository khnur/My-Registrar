package com.example.practice_1.models;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@Data
@NoArgsConstructor
public class Book {
    private Long id;

    private String name;

    private String author;

    private Course course;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public static Book getInstance(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Author: ");
        String author = scanner.next();

//        LocalDate publishedDate = null;
//        while (publishedDate == null) {
//            System.out.print("Published Date (YYYY-MM-DD): ");
//            try {
//                publishedDate = LocalDate.parse(scanner.next());
//            } catch (Exception e) {
//                System.out.println("Entered incorrect for of date. Try again\n");
//            }
//        }

        return new Book(name, author);

    }
    public static Book createRandomBook() {
        Faker faker = Faker.instance();
        com.github.javafaker.Book book = faker.book();
        String name = book.title();
        String author = book.author();

        return new Book(name, author);
    }
}
