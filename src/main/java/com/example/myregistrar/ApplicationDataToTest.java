package com.example.myregistrar;

import com.example.myregistrar.jms.KafkaService;
import com.example.myregistrar.models.*;
import com.example.myregistrar.services.*;
import com.example.myregistrar.util.NewModel;
import com.example.myregistrar.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ApplicationDataToTest {
    @Bean
    CommandLineRunner commandLineRunner(
            KafkaService kafkaService,
            StudentService studentService,
            CourseService courseService,
            BookService bookService,
            UniversityService universityService
    ) {
        return args -> {
//            enableJmsFlow(courseService, universityService); // uncomment it if you want to enable
        };
    }

    @Bean
    ApplicationRunner applicationRunner(
            StudentService studentService,
            CourseService courseService,
            BookService bookService,
            UniversityService universityService,
            EndUserService endUserService
    ) {
        return args -> {
            endUserService.createUser(new EndUser(
                    "admin",
                    "$2a$12$BO709/LgaeJi4N2Q0wlEo.5NetvwKO9hZgJYfnDhuQmQWeziyFgh.",
                    Role.ADMIN.getRoleName()
            ));

            studentService.createStudent(new Student(
                    "aaa",
                    "aaa",
                    LocalDate.of(1995, 5,6),
                    "M"
            ));

            universityService.createUniversity(new University(
                    "uuu",
                    "uuu",
                    "uuu"
            ));

            List<Course> courses = List.of(
                    new Course(
                            "aaa",
                            "aaa",
                            "aaa",
                            6
                    ),
                    new Course(
                            "bbb",
                            "aaa",
                            "aaa",
                            6
                    ),
                    new Course(
                            "ppp",
                            "aaa",
                            "aaa",
                            6
                    )
            );

            courses.forEach(course -> {
                Course saved = courseService.createCourse(course);
                courseService.assignUniversityToCourse(
                        saved,
                        universityService.getUniversityById(1L)
                );
            });

            List.of(
                    new Book(
                            "aaa",
                            "aaa",
                            "aaa",
                            LocalDate.now(),
                            "aaa",
                            141
                    ),
                    new Book(
                            "bbb",
                            "aaa",
                            "aaa",
                            LocalDate.now(),
                            "aaa",
                            252
                    ),
                    new Book(
                            "ccc",
                            "aaa",
                            "aaa",
                            LocalDate.now(),
                            "aaa",
                            378
                    )
            ).forEach(bookService::createBook);

            courseService.assignBookToCourse(
                    courseService.getCourseById(1L),
                    bookService.getBookById(1L)
            );

            courseService.assignBookToCourse(
                    courseService.getCourseById(1L),
                    bookService.getBookById(2L)
            );

            courseService.assignBookToCourse(
                    courseService.getCourseById(2L),
                    bookService.getBookById(3L)
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseById(1L),
                    courseService.getCourseById(2L)
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseById(1L),
                    courseService.getCourseById(3L)
            );

            studentService.assignUniversityToStudent(
                    studentService.getStudentById(1L),
                    universityService.getUniversityById(1L)
            );

            studentService.assignCourseToStudent(
                    studentService.getStudentById(1L),
                    courseService.getCourseById(1L)
            );
        };
    }

    private static void enableJmsFlow(CourseService courseService, UniversityService universityService) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    Course course = courseService.createCourse(NewModel.createRandomCourse());
                    course.setUniversity(
                            universityService.getUniversityById(1L)
                    );

                    courseService.notifyStudentsWithinUniversity(course);
                },
                0, 5, TimeUnit.SECONDS);
    }
}
