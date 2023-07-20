package com.example.myregistrar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table
@Data
@NoArgsConstructor
@Slf4j
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String department;

    @Column
    private String instructor;

    @Column(
            name = "credit_hours",
            nullable = false
    )
    private Integer creditHours;

    @ManyToMany(
            mappedBy = "courses",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Course(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name.trim();
        this.university = university.trim();
        this.department = department.trim();
        this.instructor = instructor.trim();
        this.creditHours = creditHours;
    }

    public static Course getInstance(Scanner scanner) {
        log.info("Name: ");
        String name = scanner.next();

        log.info("University: ");
        String university = scanner.next();

        log.info("Department: ");
        String department = scanner.next();

        log.info("Instructor: ");
        String instructor = scanner.next();

        Integer creditHours = null;
        while (creditHours == null) {
            log.info("Credit hours (ECT : 4 - 12): ");
            try {
                int temp = scanner.nextInt();

                if (temp >= 4 && temp <= 12) {
                    creditHours = temp;
                } else {
                    log.error("Invalid number of credit hours entered. Try again");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
            }
        }

        return new Course(name, university, department, instructor, creditHours);
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
