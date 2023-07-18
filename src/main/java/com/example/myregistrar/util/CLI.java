package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Lazy
@RequiredArgsConstructor
public class CLI {
    private final Scanner scanner = new Scanner(System.in);

    private final StudentService studentService;
    private final CourseService courseService;
    private final BookService bookService;
    private final RegistrationService registrationService;

    public void init() {
        System.out.println("""
                ------------------------------
                Welcome to My Registrar
                ------------------------------
                """);

        while (true) {
            System.out.print("""
                                        
                    1. Create Entity(s)
                    2. Get Entity(s)
                    3. Assign Entity(s)
                    0. Exit
                    \nPick a command number:\s""");
            try {
                switch (scanner.nextInt()) {
                    case 1 -> createEntity();
                    case 2 -> getEntity();
                    case 3 -> assignEntities();
                    case 0 -> {
                        scanner.close();
                        System.out.println("Thank you. Have fun");
                        System.exit(0);
                    }
                    default -> displayErrorMessage("number");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void createEntity() throws RuntimeException {
        System.out.print("""
                
                1. Create Student
                2. Create Course
                3. Create Book
                0. Back
                \nPick a command number:\s""");

        switch (scanner.nextInt()) {
            case 0 -> {
                init();
                return;
            }
            case 1 -> createEntityFurther(Student.class);
            case 2 -> createEntityFurther(Course.class);
            case 3 -> createEntityFurther(Book.class);
            default -> {
                displayErrorMessage("number");
            }
        }
        createEntity();
    }


    private void createEntityFurther(Class<?> entityClass) throws RuntimeException {
        System.out.printf("""
                
                a. Create Custom %1$s
                b. Create Random %1$s(s)
                0. Back
                %nPick a command char:\s""", entityClass.getSimpleName());

        String command = scanner.next();
        if (command.length() > 1) {
            displayErrorMessage("char");
            createEntityFurther(entityClass);
            return;
        }

        switch (command.charAt(0)) {
            case 'a', 'A' -> {
                if (Student.class.isAssignableFrom(entityClass)) {
                    studentService.createStudent(Student.getInstance(scanner));
                } else if (Course.class.isAssignableFrom(entityClass)) {
                    courseService.createCourse(Course.getInstance(scanner));
                } else if (Book.class.isAssignableFrom(entityClass)) {
                    bookService.createBook(Book.getInstance(scanner));
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
                    studentService.createRandomStudents(n);
                } else if (Course.class.isAssignableFrom(entityClass)) {
                    courseService.createRandomCourses(n);
                } else if (Book.class.isAssignableFrom(entityClass)) {
                    bookService.createRandomBooks(n);
                }
            }
            case '0', 0, 'o', 'O' -> createEntity();
            default -> {
                displayErrorMessage("char");
                createEntityFurther(entityClass);
            }
        }
    }

    private void getEntity() throws RuntimeException {
        System.out.print("""
                
                1. Get Student
                2. Get Course
                3. Get Book
                0. Exit
                \nPick a command number:\s""");

        switch (scanner.nextInt()) {
            case 1 -> getEntityStudent();
            case 2 -> getEntityCourse();
            case 3 -> getEntityBook();
            case 0 -> init();

            default -> displayErrorMessage("number");
        }
    }

    private void getEntityStudent() throws RuntimeException {
        System.out.print("""
                
                1. Get all students
                2. Get student(s) by first name
                3. Get student(s) by last name
                4. Get student(s) by first and last names (with further features)
                0. Exit
                \nPick a command number:\s""");

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }

                case 1 -> {
                    studentService.getAllStudents().forEach(
                            student -> System.out.println(JsonMapper.toJsonString(student))
                    );
                }
                case 2 -> {
                    System.out.print("First name: ");
                    studentService.getStudentsByFirstName(scanner.next())
                            .forEach(
                                    student -> System.out.println(JsonMapper.toJsonString(student))
                            );
                }
                case 3 -> {
                    System.out.print("Last name: ");

                    studentService.getStudentsByLastName(scanner.next()).forEach(
                            student -> System.out.println(JsonMapper.toJsonString(student))
                    );
                }

                case 4 -> {
                    System.out.print("First name: ");
                    String firstName = scanner.next();

                    System.out.print("Last name: ");
                    String lastName = scanner.next();


                    Student student = studentService.getStudentByFirstNameAndLastName(firstName, lastName);
                    System.out.println(JsonMapper.toJsonString(student));
                    getStudentsBooksAndCourses(student);
                }
                default -> displayErrorMessage("number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        getEntityStudent();
    }

    private void getStudentsBooksAndCourses(Student student) throws RuntimeException {
        System.out.print("""
                
                a. Get all assigned courses
                b. Get all books
                0. Exit
                \nPick a command char:\s""");
        String command = scanner.next();
        if (command.length() > 1) {
            displayErrorMessage("char");
            getStudentsBooksAndCourses(student);
            return;
        }

        try {
            switch (command.charAt(0)) {
                case '0', 0, 'o', 'O' -> getEntityStudent();
                case 'a', 'A' -> {
                    registrationService.getCoursesByStudent(student).forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                }
                case 'b', 'B' -> {
                    bookService.getBooksByStudent(student).forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                }
                default -> {
                    displayErrorMessage("char");
                    getStudentsBooksAndCourses(student);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getEntityCourse() throws RuntimeException {
        System.out.print("""
                
                1. Get all courses
                2. Get course(s) by name
                3. Get course(s) by university
                4. Get course(s) by name and university
                0. Exit
                \nPick a command char:\s""");

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    courseService.getAllCourses().forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                }
                case 2 -> {
                    System.out.print("Name: ");
                    courseService.getCoursesByName(scanner.next()).forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );

                }
                case 3 -> {
                    System.out.print("University: ");
                    courseService.getCoursesByUniversity(scanner.next()).forEach(
                            course -> System.out.println(JsonMapper.toJsonString(course))
                    );
                }
                case 4 -> {
                    System.out.print("Name: ");
                    String name = scanner.next();

                    System.out.print("University: ");
                    String university = scanner.next();

                    Course course = courseService.getCourseByNameAndUniversity(name, university);
                    System.out.println(JsonMapper.toJsonString(course));
                    getCoursesStudentsAndBooks(course);
                }
                default -> displayErrorMessage("number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        getEntityCourse();
    }

    private void getCoursesStudentsAndBooks(Course course) throws RuntimeException {
        System.out.print("""
                
                a. Get all students
                b. Get all books
                0. Exit
                \nPick a command char:\s""");
        String command = scanner.next();
        if (command.length() > 1) {
            displayErrorMessage("char");
            getCoursesStudentsAndBooks(course);
            return;
        }

        try {
            switch (command.charAt(0)) {
                case '0', 0, 'o', 'O' -> getEntityCourse();
                case 'a', 'A' -> registrationService.getStudentsByCourse(course).forEach(
                        student -> System.out.println(JsonMapper.toJsonString(student))
                );

                case 'b', 'B' -> bookService.getBooksByCourse(course).forEach(
                        book -> System.out.println(JsonMapper.toJsonString(book))
                );
                default -> {
                    displayErrorMessage("char");
                    getCoursesStudentsAndBooks(course);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void getEntityBook() throws RuntimeException {
        System.out.print("""
                
                1. Get all books
                2. Get book(s) by name
                3. Get book(s) by author
                4. Get book by name and author
                0. Exit
                \nPick a command char:\s""");

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    bookService.getAllBooks().forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                }
                case 2 -> {
                    System.out.print("Name: ");
                    bookService.getBooksByName(scanner.next()).forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                }
                case 3 -> {
                    System.out.print("Author: ");
                    bookService.getBooksByAuthor(scanner.next()).forEach(
                            book -> System.out.println(JsonMapper.toJsonString(book))
                    );
                }
                case 4 -> {
                    System.out.print("Name: ");
                    String name = scanner.next();

                    System.out.print("Author: ");
                    String author = scanner.next();

                    System.out.println(JsonMapper.toJsonString(bookService.getBookByNameAndAuthor(name, author)));
                }
                default -> displayErrorMessage("number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        getEntityBook();
    }

    private void assignEntities() throws RuntimeException {
        System.out.print("""
                
                1. Assign student to course
                2. Assign course to books
                0. Exit
                \nPick a command char:\s""");
        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    init();
                    return;
                }
                case 1 -> {
                    System.out.print("Student first name: ");
                    String firstName = scanner.next();

                    System.out.print("Student last name: ");
                    String lastName = scanner.next();

                    Student student = studentService.getStudentByFirstNameAndLastName(firstName, lastName);

                    System.out.print("Course name: ");
                    String courseName = scanner.next();

                    List<Course> courses = courseService.getCoursesByName(courseName);

                    registrationService.assignCoursesToStudent(student, courses);
                }
                case 2 -> {
                    System.out.print("Course name: ");
                    String courseName = scanner.next();

                    System.out.print("Course university: ");
                    String university = scanner.next();

                    Course course = courseService.getCourseByNameAndUniversity(courseName, university);

                    System.out.print("Book name: ");
                    String bookName = scanner.next();

                    List<Book> books = bookService.getBooksByName(bookName);

                    courseService.assignBooksToCourse(course, books);
                }
                default -> displayErrorMessage("number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assignEntities();
    }

    private void displayErrorMessage(String errType) {
        System.out.println("Invalid command " + errType + " picked. Try again\n");
    }
}
