package com.example.practice_1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@NoArgsConstructor
public class Course {
    private Long id;


    private String name;

    private String university;


    @ToString.Exclude
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Course(String name, String university) {
        this.name = name;
        this.university = university;
    }

    public static Course getInstance(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("University: ");
        String university = scanner.next();

        return new Course(name, university);
    }

    public static Course createRandomCourse() {
        Faker faker = Faker.instance();
        String name = faker.programmingLanguage().name();
        String school = faker.university().name();

        return new Course(name, school);
    }
}
