package com.example.practice_1.util;

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
    private final Scanner scanner;

    public void init() {
        System.out.println("""
                ------------------------------
                Welcome to My Registrar
                """);

        while (true) {
            System.out.println("""
                    1. Create Entity
                    2. Pick Entity
                    3. Assign Entity
                    """);
            int command = scanner.nextInt();

            try {
                if (!checkCommandCorrectness(command)) continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Thank you. Have fun");
                System.exit(0);
            }

            try {
                switch (command) {
                    case 1 -> createEntity();

                    case 2 -> {

                    }
                    case 3 -> {

                    }
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
                \nPick a command number:""");
        int command = scanner.nextInt();
        if (!checkCommandCorrectness(command)) {
            createEntity();
        } else {
            commandNumber(command);
        }
    }


    private void commandNumber(int command) {
        switch (command) {
            case 1 -> {
                System.out.println("""
                        \ta. Create Custom Student
                        \tb. Create Random Student(s)
                        """);
                switch (scanner.next().charAt(0)) {
                    case 'a' -> {

                    }
                    case 'b' -> {
                        System.out.println("");
                    }
                }
            }
            case 2 -> {

            }
            case 3 -> {

            }
        }
    }

    private boolean checkCommandCorrectness(int command) throws RuntimeException {
        if (command < 0 || command > 3) {
            System.err.println("Invalid command number picked. Try again");
            return false;
        } else if (command == 0) {
            throw new RuntimeException("Exit command performed");
        }
        return true;
    }

}
