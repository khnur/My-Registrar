package com.example.myregistrar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_seq"
    )
    @SequenceGenerator(
            name = "course_seq",
            sequenceName = "course_sequence",
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
    private String university;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
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
