package com.example.myregistrar.models;

import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Data
@NoArgsConstructor
public class Student {
    private long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private Integer age;

    private String gender;

    private String email;

    @ToString.Exclude
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    public Student(String firstName, String lastName, Date birthDate, Integer age, String gender, String email) {
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.birthDate = birthDate;
        this.age = age;
        this.gender = gender.trim();
        this.email = email.trim();
    }

    public static Student getInstance(Scanner scanner) {
        System.out.print("First Name: ");
        String firstName = scanner.next();

        System.out.print("Last Name: ");
        String lastName = scanner.next();

        Date birthDate = null;
        while (birthDate == null) {
            System.out.print("Birth Date (" + DateMapper.PATTERN + "): ");
            try {
                birthDate = DateMapper.DATE_FORMAT.parse(scanner.next().trim());
            } catch (Exception e) {
                System.out.println("Entered incorrect for of date. Try again\n");
            }
        }

        int age = DateMapper.GET_AGE(birthDate);

        String gender = null;

        while (gender == null) {
            System.out.print("Gender: ");
            gender = scanner.next();

            if (gender.startsWith("M") || gender.startsWith("m")) {
                gender = "Male";
            } else if (gender.startsWith("F") || gender.startsWith("f")) {
                gender = "Female";
            } else {
                gender = null;
                System.out.println("Entered invalid format of gender. Try again\n");
            }
        }

        String email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz";

        return new Student(firstName, lastName, birthDate, age, gender, email);
    }

    public static Student createRandomStudent() {
        Faker faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        int age = DateMapper.GET_AGE(date);
        String gender = faker.random().nextInt(5) % 2 == 0 ? "Male" : "Female";
        String email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz";

        return new Student(firstName, lastName, date, age, gender, email);
    }
}
