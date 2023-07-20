package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@Component
@Lazy
@RequiredArgsConstructor
@Slf4j
public class CLI {
    private final Scanner scanner = new Scanner(System.in);

    private final StudentService studentService;
    private final CourseService courseService;
    private final BookService bookService;

    private boolean running = false;

    public void init() {
        running = true;

        log.info("------------------------------");
        log.info("Welcome to My Registrar");
        log.info("------------------------------");

        while (running) {
            mainMenu();
        }
        scanner.close();
        log.info("Thank you. Have fun");
        System.exit(0);
    }

    private void mainMenu() {
        displayMenu(
                "Create Entity(s)",
                "Get Entity(s)",
                "Assign Entity(s)"
        );

        try {
            switch (scanner.nextInt()) {
                case 1 -> createEntity();
                case 2 -> getEntity();
                case 3 -> assignEntities();
                case 0 -> running = false;
                default -> displayErrorMessage();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            scanner.nextLine();
        }
    }

    private void createEntity() throws RuntimeException {
        displayMenu(
                "Create Student",
                "Create Course",
                "Create Book"
        );

        switch (scanner.nextInt()) {
            case 0 -> {
                mainMenu();
                return;
            }
            case 1 -> createEntityFurther(Student.class);
            case 2 -> createEntityFurther(Course.class);
            case 3 -> createEntityFurther(Book.class);
            default -> displayErrorMessage();
        }

        createEntity();
    }


    private void createEntityFurther(Class<?> entityClass) throws RuntimeException {
        displayMenu(
                String.format("Create Custom %1$s", entityClass.getSimpleName()),
                String.format("Create Random %1$s", entityClass.getSimpleName())
        );

        switch (scanner.nextInt()) {
            case 1 -> {
                if (Student.class.isAssignableFrom(entityClass)) {
                    studentService.createStudent(Student.getInstance(scanner));
                } else if (Course.class.isAssignableFrom(entityClass)) {
                    courseService.createCourse(Course.getInstance(scanner));
                } else if (Book.class.isAssignableFrom(entityClass)) {
                    bookService.createBook(Book.getInstance(scanner));
                }
            }
            case 2 -> {
                int n;
                do {
                    log.info("How many: ");
                    n = scanner.nextInt();

                    if (n < 0 || n > 100) {
                        log.error("Invalid amount or it is too large\n");
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
            case 0 -> createEntity();
            default -> {
                displayErrorMessage();
                createEntityFurther(entityClass);
            }
        }
    }

    private void getEntity() throws RuntimeException {
        displayMenu(
                "Get Student(s)",
                "Get Course(s)",
                "Get Book(s)"
        );

        switch (scanner.nextInt()) {
            case 1 -> getEntityStudent();
            case 2 -> getEntityCourse();
            case 3 -> getEntityBook();
            case 0 -> mainMenu();

            default -> displayErrorMessage();
        }
    }

    private void getEntityStudent() throws RuntimeException {
        displayMenu(
                "Get all students",
                "Get student(s) by first name",
                "Get student(s) by last name",
                "Get student(s) by first and last names (with further features)"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }

                case 1 -> studentService.getAllStudents().forEach(
                        student -> log.info(JsonMapper.toJsonString(student))
                );

                case 2 -> {
                    String firstName = getUserInput("First name: ");
                    studentService.getStudentsByFirstName(firstName)
                            .forEach(
                                    student -> log.info(JsonMapper.toJsonString(student))
                            );
                }
                case 3 -> {
                    String lastName = getUserInput("Last name: ");
                    studentService.getStudentsByLastName(lastName).forEach(
                            student -> log.info(JsonMapper.toJsonString(student))
                    );
                }

                case 4 -> {
                    String firstName = getUserInput("First name: ");

                    String lastName = getUserInput("Last name: ");

                    Student student = studentService.getStudentByFirstNameAndLastName(firstName, lastName);
                    log.info(JsonMapper.toJsonString(student));

                    getStudentsBooksAndCourses(student);
                }
                default -> displayErrorMessage();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            scanner.nextLine();
        }
        getEntityStudent();
    }

    private void getStudentsBooksAndCourses(Student student) throws RuntimeException {
        displayMenu(
                "Get all assigned courses",
                "Get all books"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> getEntityStudent();
                case 1 -> courseService.getCoursesByStudent(student).forEach(
                        course -> log.info(JsonMapper.toJsonString(course))
                );

                case 2 -> bookService.getBooksByStudent(student).forEach(
                        book -> log.info(JsonMapper.toJsonString(book))
                );

                default -> {
                    displayErrorMessage();
                    getStudentsBooksAndCourses(student);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void getEntityCourse() throws RuntimeException {
        displayMenu(
                "Get all courses",
                "Get course(s) by name",
                "Get course(s) by university",
                "Get course(s) by name and university"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }
                case 1 -> courseService.getAllCourses().forEach(
                        course -> log.info(JsonMapper.toJsonString(course))
                );

                case 2 -> {
                    String name = getUserInput("Name: ");
                    courseService.getCoursesByName(name).forEach(
                            course -> log.info(JsonMapper.toJsonString(course))
                    );

                }
                case 3 -> {
                    String university = getUserInput("University: ");
                    courseService.getCoursesByUniversity(university).forEach(
                            course -> log.info(JsonMapper.toJsonString(course))
                    );
                }
                case 4 -> {
                    String name = getUserInput("Name: ");
                    String university = getUserInput("University: ");

                    Course course = courseService.getCourseByNameAndUniversity(name, university);
                    log.info(JsonMapper.toJsonString(course));

                    getCoursesStudentsAndBooks(course);
                }
                default -> displayErrorMessage();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            scanner.nextLine();
        }
        getEntityCourse();
    }

    private void getCoursesStudentsAndBooks(Course course) throws RuntimeException {
        displayMenu(
                "Get all students",
                "Get all books"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> getEntityCourse();
                case 1 -> studentService.getStudentsByCourse(course).forEach(
                        student -> log.info(JsonMapper.toJsonString(student))
                );

                case 2 -> bookService.getBooksByCourse(course).forEach(
                        book -> log.info(JsonMapper.toJsonString(book))
                );
                default -> {
                    displayErrorMessage();
                    getCoursesStudentsAndBooks(course);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private void getEntityBook() throws RuntimeException {
        displayMenu(
                "Get all books",
                "Get book(s) by name",
                "Get book(s) by author",
                "Get book by name and author"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    return;
                }
                case 1 -> bookService.getAllBooks().forEach(
                        book -> log.info(JsonMapper.toJsonString(book))
                );

                case 2 -> {
                    String name = getUserInput("Name: ");
                    bookService.getBooksByName(name).forEach(
                            book -> log.info(JsonMapper.toJsonString(book))
                    );
                }
                case 3 -> {
                    String author = getUserInput("Author: ");
                    bookService.getBooksByAuthor(author).forEach(
                            book -> log.info(JsonMapper.toJsonString(book))
                    );
                }
                case 4 -> {
                    String name = getUserInput("Name: ");
                    String author = getUserInput("Author: ");

                    log.info(JsonMapper.toJsonString(bookService.getBookByNameAndAuthor(name, author)));
                }
                default -> displayErrorMessage();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            scanner.nextLine();
        }
        getEntityBook();
    }

    private void assignEntities() throws RuntimeException {
        displayMenu(
                "Assign student to course",
                "Assign course to books"
        );

        try {
            switch (scanner.nextInt()) {
                case 0 -> {
                    mainMenu();
                    return;
                }
                case 1 -> {
                    String firstName = getUserInput("Student first name: ");

                    String lastName = getUserInput("Student last name: ");

                    Student student = studentService.getStudentByFirstNameAndLastName(firstName, lastName);

                    String courseName = getUserInput("Course name: ");

                    List<Course> courses = courseService.getCoursesByName(courseName);

                    studentService.assignCoursesToStudent(student, courses);
                }
                case 2 -> {
                    String courseName = getUserInput("Course name: ");

                    String university = getUserInput("University: ");

                    Course course = courseService.getCourseByNameAndUniversity(courseName, university);

                    String bookName = getUserInput("Book name: ");

                    List<Book> books = bookService.getBooksByName(bookName);

                    courseService.assignBooksToCourse(course, books);
                }
                default -> displayErrorMessage();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            scanner.nextLine();
        }
        assignEntities();
    }

    private void displayErrorMessage() {
        log.error("Invalid command number picked. Try again\n");
    }

    private void displayMenu(String... options) {
        log.info("");
        IntStream.range(0, options.length)
                .forEach(i -> log.info(String.format("%d. %s", i + 1, options[i])));
        log.info("0. Back\n");
        log.info("Pick a command number: ");
    }

    private String getUserInput(String prompt) {
        log.info(prompt);
        return scanner.next();
    }
}
