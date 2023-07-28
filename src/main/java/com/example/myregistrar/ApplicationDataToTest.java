package com.example.myregistrar;

import com.example.myregistrar.jms.KafkaService;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.DateMapper;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.NewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
            enableJmsFlow(courseService, universityService); // comment it if you want to disable
        };
    }

    @Bean
    ApplicationRunner applicationRunner(
            StudentService studentService,
            CourseService courseService,
            BookService bookService,
            UniversityService universityService
    ) {
        return args -> {
            studentService.createStudent(new Student(
                    "aaa",
                    "aaa",
                    DateMapper.DATE_FORMAT.parse("1234-78-78"),
                    "M",
                    "aaa",
                    "ROLE_USER",
                    true
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
                            DateMapper.DATE_FORMAT.parse("1234-78-78"),
                            "aaa",
                            141
                    ),
                    new Book(
                            "bbb",
                            "aaa",
                            "aaa",
                            DateMapper.DATE_FORMAT.parse("1234-78-78"),
                            "aaa",
                            252
                    ),
                    new Book(
                            "ccc",
                            "aaa",
                            "aaa",
                            DateMapper.DATE_FORMAT.parse("1234-78-78"),
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

            log.info(JsonMapper.toJsonString(studentService.getStudentReport(
                    studentService.getStudentById(1L)
            )));
        };
    }

    private static void enableJmsFlow(CourseService courseService, UniversityService universityService) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    Course randomCourse = NewModel.createRandomCourse();
                    randomCourse.setUniversity(
                            universityService.getUniversityById(1L)
                    );

                    courseService.createCourse(randomCourse);
                },
                0, 5, TimeUnit.SECONDS);
    }
}
