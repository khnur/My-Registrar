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
public class Student {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    @ToString.Exclude
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    public Student(String firstName, String lastName, Integer age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public static Student getInstance(Scanner scanner) {
        System.out.print("First Name: ");
        String firstName = scanner.next();

        System.out.print("Last Name: ");
        String lastName = scanner.next();

        System.out.print("Age: ");
        int age = scanner.nextInt();

        return new Student(
                firstName,
                lastName,
                age,
                firstName.toLowerCase() + '.' + lastName.toLowerCase() + "onelab.kz"
        );
    }

    public static Student createRandomStudent() {
        Faker faker = Faker.instance();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        return new Student(
                firstName,
                lastName,
                faker.number().numberBetween(19, 33),
                firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz"
        );
    }
}
