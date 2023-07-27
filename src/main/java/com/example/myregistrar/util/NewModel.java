package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class NewModel {
    private static final Faker faker = Faker.instance();
    public static Course getCourseInstance() throws IOException {
        log.info("Name: ");
        String name = ConsoleInput.readLine();

        log.info("Department: ");
        String department = ConsoleInput.readLine();

        log.info("Instructor: ");
        String instructor = ConsoleInput.readLine();

        Integer creditHours = null;
        while (creditHours == null) {
            log.info("Credit hours (ECT : 4 - 12): ");
            try {
                int temp = ConsoleInput.readInt();

                if (temp >= 4 && temp <= 12) {
                    creditHours = temp;
                } else {
                    log.error("Invalid number of credit hours entered. Try again");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return new Course(name, department, instructor, creditHours);
    }

    public static Course createRandomCourse() {
        String name = faker.programmingLanguage().name();
        String department = faker.educator().course();
        String instructor = faker.funnyName().name();
        Integer creditHours = faker.number().numberBetween(4, 12);

        return new Course(name, department, instructor, creditHours);
    }

    public static Book getBookInstance() throws IOException {
        log.info("Name: ");
        String name = ConsoleInput.readLine();

        log.info("Author: ");
        String author = ConsoleInput.readLine();

        log.info("Genre: ");
        String genre = ConsoleInput.readLine();

        Date publishedDate = null;
        while (publishedDate == null) {
            log.info("Published Date (" + DateMapper.PATTERN + "): ");
            try {
                publishedDate = DateMapper.DATE_FORMAT.parse(ConsoleInput.readLine());
            } catch (Exception e) {
                log.error("Entered incorrect for of date. Try again\n");
            }
        }

        log.info("Publisher: ");
        String publisher = ConsoleInput.readLine();

        return new Book(name, author, genre, publishedDate, publisher);
    }

    public static Book createRandomBook() {
        com.github.javafaker.Book book = faker.book();

        String name = book.title();
        String author = book.author();
        String genre = book.genre();
        Date publishedDate = faker.date().birthday();
        String publisher = book.publisher();

        return new Book(name, author, genre, publishedDate, publisher);
    }

//    public static Student getStudentInstance() throws IOException {
//        log.info("First Name: ");
//        String firstName = ConsoleInput.readLine();
//
//        log.info("Last Name: ");
//        String lastName = ConsoleInput.readLine();
//
//        Date birthDate = null;
//        while (birthDate == null) {
//            log.info("Birth Date (" + DateMapper.PATTERN + "): ");
//            try {
//                birthDate = DateMapper.DATE_FORMAT.parse(ConsoleInput.readLine());
//            } catch (Exception e) {
//                log.error("Entered incorrect for of date. Try again\n");
//            }
//        }
//
//        String gender = null;
//
//        while (gender == null) {
//            log.info("Gender: ");
//            gender = ConsoleInput.readLine();
//
//            if (gender.startsWith("M") || gender.startsWith("m")) {
//                gender = "Male";
//            } else if (gender.startsWith("F") || gender.startsWith("f")) {
//                gender = "Female";
//            } else {
//                gender = null;
//                log.error("Entered invalid format of gender. Try again\n");
//            }
//        }
//
//        return new Student(firstName, lastName, birthDate, gender);
//    }

    public static Student createRandomStudent() {String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        String gender = faker.random().nextInt(5) % 2 == 0 ? "Male" : "Female";
        String password = faker.name().username();
        String role = "ROLE_USER";
        boolean isActive = true;

        return new Student(firstName, lastName, date, gender, password, role, isActive);
    }

    public static University getUniversityInstance() throws IOException {
        System.out.print("Name: ");
        String name = ConsoleInput.readLine();

        System.out.print("Country: ");
        String country = ConsoleInput.readLine();

        System.out.print("City: ");
        String city = ConsoleInput.readLine();

        return new University(name, country, city);
    }

    public static University createRandomUniversity() {
        String name = faker.university().name();
        String country = faker.country().name();
        String city = faker.gameOfThrones().city();

        return new University(name, country, city);
    }

    private NewModel() {
        throw new IllegalStateException("NewModel class created");
    }
}
