package com.example.practice_1.util;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Student;
import com.example.practice_1.repos.StudentRepo;
import com.example.practice_1.services.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
                    1. Create Entity
                    2. Pick Entity
                    3. Assign Entity
                    0. Exit
                    \nPick a command number:\s""");
            try {
                switch (scanner.nextInt()) {
                    case 1 -> createEntity();

                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 0 -> {
                        System.out.println("Thank you. Have fun");
                        System.exit(0);
                    }
                    default -> System.err.println("Invalid command number picked. Try again");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                System.err.println("Invalid command number picked. Try again");
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
            System.err.println("Invalid command char picked. Try again");
            createEntityFurther(entityClass);
        }

        switch (command.charAt(0)) {
            case 'a', 'A' -> {
                if (Student.class.isAssignableFrom(entityClass)) {
                    service.createStudent(Student.getInstance(scanner));
                }
            }
            case 'b', 'B' -> {
                int n;
                do {
                    System.out.print("How many: ");
                    n = scanner.nextInt();

                    if (n < 0 || n > 100) {
                        System.err.println("Invalid amount or it is too large");
                    }
                } while (n < 0 || n > 100);

                if (Student.class.isAssignableFrom(entityClass)) {
                    service.createRandomStudents(n);
                }
            }
            case '0', 0, 'o', 'O' -> throw new RuntimeException();
            default -> {
                System.err.println("Invalid command char picked. Try again");
                createEntityFurther(entityClass);
            }
        }
    }
}
