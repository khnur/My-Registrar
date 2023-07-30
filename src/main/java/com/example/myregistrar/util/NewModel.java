package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class NewModel {
    private static final Faker faker = Faker.instance();

    public static Course createRandomCourse() {
        String name = faker.programmingLanguage().name();
        String department = faker.educator().course();
        String instructor = faker.funnyName().name();
        Integer creditHours = faker.number().numberBetween(4, 12);

        return new Course(name, department, instructor, creditHours);
    }

    public static Book createRandomBook() {
        com.github.javafaker.Book book = faker.book();

        String name = book.title();
        String author = book.author();
        String genre = book.genre();
        LocalDate publishedDate = LocalDate.now();
        String publisher = book.publisher();
        int pageNumber = faker.number().numberBetween(100, 500);

        return new Book(name, author, genre, publishedDate, publisher, pageNumber);
    }

    public static Student createRandomStudent() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        String gender = faker.random().nextInt(5) % 2 == 0 ? "Male" : "Female";

        return new Student(firstName, lastName, date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gender);
    }

    public static University createRandomUniversity() {
        String name = faker.university().name();
        String country = faker.country().name();
        String city = faker.gameOfThrones().city();

        return new University(name, country, city);
    }
}
