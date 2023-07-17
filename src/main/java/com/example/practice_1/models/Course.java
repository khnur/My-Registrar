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
    private long id;

    private String name;

    private String university;

    private String department;

    private String instructor;

    private Integer creditHours;


    @ToString.Exclude
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Course(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.university = university;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }

    public static Course getInstance(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("University: ");
        String university = scanner.next();

        System.out.print("Department: ");
        String deparment = scanner.next();

        System.out.print("Instructor: ");
        String insrtuctor = scanner.next();

        Integer creditHours = null;
        while (creditHours == null) {
            System.out.print("Credit hours (ECT : 4 - 12): ");
            int temp = scanner.nextInt();

            if (temp >= 4 && temp <= 12) {
                creditHours = temp;
            } else {
                System.out.println("Invalid number of credit hours entered. Try again");
            }
        }

        return new Course(name, university, deparment, insrtuctor, creditHours);
    }

    public static Course createRandomCourse() {
        Faker faker = Faker.instance();

        String name = faker.programmingLanguage().name();
        String university = faker.university().name();
        String department = faker.educator().course();
        String instructor = faker.funnyName().name();
        Integer creditHours = faker.number().numberBetween(4, 12);

        return new Course(name, university, department, instructor, creditHours);
    }
}
