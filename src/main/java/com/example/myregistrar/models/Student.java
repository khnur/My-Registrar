package com.example.myregistrar.models;

import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Entity
@Table
@Data
@NoArgsConstructor
@Slf4j
public class Student {
    private static final String DOMAIN = "@onelab.kz";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "birth_date",
            nullable = false
    )
    private Date birthDate;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String email;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            },
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    public Student(String firstName, String lastName, Date birthDate, String gender) {
        firstName = firstName.trim();
        lastName = lastName.trim();

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = DateMapper.GET_AGE(birthDate);
        this.gender = gender.trim();
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + DOMAIN;
    }

    public static Student getInstance(Scanner scanner) {
        log.info("First Name: ");
        String firstName = scanner.next();

        log.info("Last Name: ");
        String lastName = scanner.next();

        Date birthDate = null;
        while (birthDate == null) {
            log.info("Birth Date (" + DateMapper.PATTERN + "): ");
            try {
                birthDate = DateMapper.DATE_FORMAT.parse(scanner.next().trim());
            } catch (Exception e) {
                log.error("Entered incorrect for of date. Try again\n");
            }
        }

        String gender = null;

        while (gender == null) {
            log.info("Gender: ");
            gender = scanner.next();

            if (gender.startsWith("M") || gender.startsWith("m")) {
                gender = "Male";
            } else if (gender.startsWith("F") || gender.startsWith("f")) {
                gender = "Female";
            } else {
                gender = null;
                log.error("Entered invalid format of gender. Try again\n");
            }
        }

        return new Student(firstName, lastName, birthDate, gender);
    }

    public static Student createRandomStudent() {
        Faker faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        String gender = faker.random().nextInt(5) % 2 == 0 ? "Male" : "Female";

        return new Student(firstName, lastName, date, gender);
    }
}
