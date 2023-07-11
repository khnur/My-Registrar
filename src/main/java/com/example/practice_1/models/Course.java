package com.example.practice_1.models;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Book> books = new ArrayList<>();

    public Course(String name, String university) {
        this.name = name;
        this.university = university;
    }

    public static Course createRandomCourse() {
        Faker faker = Faker.instance();
        String name = faker.programmingLanguage().name();
        String school = faker.university().name();

        return new Course(name, school);
    }
}
