package com.example.practice_1.util;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Student;
import com.example.practice_1.services.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.scanner.ScannerException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
@Lazy
@RequiredArgsConstructor
public class CLI {
    private final Service service;
    private final Scanner scanner = new Scanner(System.in);

    public void init() {
        System.out.println("""
                ------------------------------
                Welcome to My Registrar
                ------------------------------
                """);

        while (true) {
            System.out.println("""
                    
                    1. Create Entity(s)
                    2. Get Entity(s)
                    3. Assign Entity(s)
                    0. Exit
                    \nPick a command number:\s""");
            try {
                switch (scanner.nextInt()) {
                    case 1 -> createEntity();
                    case 2 -> getEntity();
                    case 3 -> {

                    }
                    case 0 -> {
                        scanner.close();
                        System.out.println("Thank you. Have fun");
                        System.exit(0);
                    }
                    default -> displayErrorMessage("number");
                }
            }  catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void createEntity() throws RuntimeException {
        System.out.println("""
                1. Create Student
                2. Create Course
                3. Create Book
                0. Exit
                \nPick a command number:\s""");

        switch (scanner.nextInt()) {
            case 1 -> createEntityFurther(Student.class);
            case 2 -> createEntityFurther(Course.class);
            case 3 -> createEntityFurther(Book.class);
            case 0 -> throw new RuntimeException();
            default -> {
                displayErrorMessage("number");
                createEntity();
            }
        }
    }


    private void createEntityFurther(Class<?> entityClass) throws RuntimeException {
        System.out.printf("""
                a. Create Custom %1$s
                b. Create Random %1$s(s)
                0. Exit\s
                %nPick a command char:\s""", entityClass.getSimpleName());

        String command = scanner.next();
        if (command.length() > 1) {
            displayErrorMessage("char");
            createEntityFurther(entityClass);
        }

        switch (command.charAt(0)) {
            case 'a', 'A' -> {
                if (Student.class.isAssignableFrom(entityClass)) {
                    service.createStudent(Student.getInstance(scanner));
                } else if (Course.class.isAssignableFrom(entityClass)) {
                    service.createCourse(Course.getInstance(scanner));
                } else if (Book.class.isAssignableFrom(entityClass)) {
                    service.createBook(Book.getInstance(scanner));
                }
            }
            case 'b', 'B' -> {
                int n;
                do {
                    System.out.print("How many: ");
                    n = scanner.nextInt();

                    if (n < 0 || n > 100) {
                        System.out.println("Invalid amount or it is too large\n");
                    }
                } while (n < 0 || n > 100);

                if (Student.class.isAssignableFrom(entityClass)) {
                    service.createRandomStudents(n);
                } else if (Course.class.isAssignableFrom(entityClass)) {
                    service.createRandomCourses(n);
                } else if (Book.class.isAssignableFrom(entityClass)) {
                    service.createRandomBooks(n);
                }
            }
            case '0', 0, 'o', 'O' -> throw new RuntimeException();
            default -> {
                displayErrorMessage("char");
                createEntityFurther(entityClass);
            }
        }
    }

    private void getEntity() throws RuntimeException {
        System.out.println("""
                1. Get Student
                2. Get Course
                3. Get Book
                0. Exit
                \nPick a command number:\s""");

        switch (scanner.nextInt()) {
            case 1 -> getEntityStudent();
            case 2 -> getEntityCourse();
            case 3 -> getEntityBook();
            case 0 -> throw new RuntimeException();
            default -> {
                displayErrorMessage("number");
                getEntity();
            }
        }
    }

    private void getEntityStudent() throws RuntimeException {
        System.out.println("""
                1. Get all students
                2. Get student(s) by first name
                3. Get student(s) by last name
                4. Get student(s) by first and last names (with further features)
                0. Exit
                \nPick a command number:\s""");

        switch (scanner.nextInt()) {
            case 0 -> throw new RuntimeException();
            case 1 -> {
                try {
                    service.getAllStudents().forEach(
                            student -> System.out.println(JsonMapper.toJsonString(student))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("First name: ");
                try {
                    service.getStudentsByFirstName(scanner.next())
                            .forEach(
                                    student -> System.out.println(JsonMapper.toJsonString(student))
                            );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("Last name: ");
                try {
                    service.getStudentsByLastName(scanner.next()).forEach(
                            student -> System.out.println(JsonMapper.toJsonString(student))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            case 4 -> {
                System.out.print("First name: ");
                String firstName = scanner.next();

                System.out.print("Last name: ");
                String lastName = scanner.next();

                try {
                    Student student = service.getStudentByFirstNameAndLastName(firstName, lastName);
                    System.out.println(JsonMapper.toJsonString(student));
                    getStudentsBooksAndCourses(student);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> displayErrorMessage("number");

        }
        getEntityStudent();
    }

    private void getStudentsBooksAndCourses(Student student) throws RuntimeException {
        System.out.println("""
                a. Get all assigned courses
                b. Get all books
                0. Exit
                \nPick a command char:\s""");
        String command = scanner.next();
        if (command.length() > 1) {
            displayErrorMessage("char");
            getStudentsBooksAndCourses(student);
        }

        switch (command.charAt(0)) {
            case '0', 0, 'o', 'O' -> throw new RuntimeException();
            case 'a', 'A' -> {

            }
            case 'b', 'B' -> {

            }
            default -> {
                displayErrorMessage("char");
                getStudentsBooksAndCourses(student);
            }
        }
    }

    private void getEntityCourse() throws RuntimeException {
        System.out.println("""
                1. Get all courses
                2. Get course(s) by name
                3. Get course(s) by university
                4. Get course(s) by name and university
                0. Exit
                \nPick a command char:\s""");

        switch (scanner.nextInt()) {
            case 0 -> throw new RuntimeException();
            case 1 -> {
                try {
                    service.getAllCourses().forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Name: ");
                try {
                    service.getCoursesByName(scanner.next()).forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("University: ");
                try {
                    service.getCoursesByUniversity(scanner.next()).forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 4 -> {
                System.out.print("Name: ");
                String name = scanner.next();

                System.out.print("University: ");
                String university = scanner.next();

                try {
                    Course course = service.getCourseByNameAndUniversity(name, university);
                    System.out.println(JsonMapper.toJsonString(course));
                    getCoursesStudentsAndBooks(course);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> displayErrorMessage("number");
        }
        getEntityCourse();
    }

    private void getCoursesStudentsAndBooks(Course course) throws RuntimeException {

    }


    private void getEntityBook() throws RuntimeException {
        System.out.println("""
                1. Get all books
                2. Get book(s) by name
                3. Get book(s) by author
                4. Get book by name and author
                0. Exit
                \nPick a command char:\s""");

        switch (scanner.nextInt()) {
            case 0 -> throw new RuntimeException();
            case 1 -> {
                try {
                    service.getAllBooks().forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Name: ");
                try {
                    service.getBooksByName(scanner.next()).forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("Author: ");
                try {
                    service.getBooksByAuthor(scanner.next()).forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case 4 -> {
                System.out.print("Name: ");
                String name = scanner.next();

                System.out.print("Author: ");
                String author = scanner.next();

                try {
                    System.out.println(JsonMapper.toJsonString(service.getBookByNameAndAuthor(name, author)));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> displayErrorMessage("number");
        }
        getEntityBook();
    }

    private void assignEntities() throws RuntimeException {


    }

    private void displayErrorMessage(String errType) {
        System.out.println("Invalid command " + errType + " picked. Try again\n");
    }
}
