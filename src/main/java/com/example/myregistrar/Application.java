package com.example.myregistrar;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.jms.KafkaService;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.DateMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
   - Whenever a new course is created, the course notification system automatically sends notifications to all students who have subscribed for course updates.
   - The course acceptance system considers the age of students before enrolling them in a course. Students whose age falls within the accepted range will be enrolled in the course automatically.
   - The system plans to introduce the `University` model in future updates to represent different universities.
   - Each student will be associated with a specific university, allowing for personalized notifications and handling.
   - As part of future enhancements, the system will provide each student in the university with a unique consumer.
   - This will hopefully enable targeted notifications and allow students to receive course updates tailored to their preferences.
     */

    @Bean
    CommandLineRunner commandLineRunner(
            KafkaService kafkaService,
            StudentService studentService,
            CourseService courseService,
            BookService bookService
    ) {
        return args -> {
            enableJmsFlow(kafkaService, courseService); // comment it if you want to disable

            /*
                The custom code goes here
             */

        };
    }

    @Bean
    ApplicationRunner applicationRunner(
            StudentService studentService,
            CourseService courseService,
            BookService bookService
    ) {
        return args -> {
            studentService.generateRandomStudents(5);
            bookService.generateRandomBooks(10);

            List.of(
                    new StudentDto(
                            "aaa",
                            "bbb",
                            DateMapper.DATE_FORMAT.parse("2020-14-74"),
                            "M"
                    ),
                    new StudentDto(
                            "aaa",
                            "ppp",
                            DateMapper.DATE_FORMAT.parse("2020-14-74"),
                            "M"
                    )
            ).forEach(studentDto -> studentService.createStudent(studentDto.toStudent()));

            List.of(
                    new CourseDto(
                            "aaa",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "bbb",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "qqq",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "www",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    )
            ).forEach(courseDto -> courseService.createCourse(courseDto.toCourse()));

            List.of(
                    new BookDto(
                            "nnn",
                            "mmm",
                            "mmm",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "qwfewrf"
                    ),
                    new BookDto(
                            "nnn",
                            "qqq",
                            "mmm",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "qwfewrf"
                    )
            ).forEach(bookDto -> bookService.createBook(bookDto.toBook()));

            courseService.assignBooksToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    bookService.getBooksByName("nnn")
            );

            courseService.assignStudentsToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    studentService.getAllStudents()
            );


            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("qqq", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("qqq", "ppp"),
                    courseService.getCourseByNameAndUniversity("www", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("www", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );

            courseService.removeCoursePreRequisiteFromCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );
        };
    }

    private static void enableJmsFlow(KafkaService kafkaService, CourseService courseService) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    Course course = CourseDto.createRandomCourseDto().toCourse();
                    courseService.createCourse(course);
                    kafkaService.sendToCourseTopic(course.toCourseDto().toJson());
                },
                0, 5, TimeUnit.SECONDS);
    }
}
