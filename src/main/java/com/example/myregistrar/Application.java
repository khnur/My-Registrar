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
import com.example.myregistrar.util.NewModel;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
            BookService bookService,
            UniversityService universityService
    ) {
        return args -> {
//            enableJmsFlow(kafkaService, courseService); // comment it if you want to disable
            studentService.createStudent(NewModel.createRandomStudent());
            universityService.createUniversity(NewModel.createRandomUniversity());
            /*
                The custom code goes here
             */

        };
    }

//    @Bean
//    ApplicationRunner applicationRunner(
//            StudentService studentService,
//            CourseService courseService,
//            BookService bookService,
//            UniversityService universityService
//    ) {
//        return args -> {
//            studentService.generateRandomStudents(5);
//            bookService.generateRandomBooks(10);
//
//            List.of(
//                    new Student(
//                            "aaa",
//                            "bbb",
//                            DateMapper.DATE_FORMAT.parse("2020-14-74"),
//                            "M"
//                    ),
//                    new Student(
//                            "aaa",
//                            "ppp",
//                            DateMapper.DATE_FORMAT.parse("2020-14-74"),
//                            "M"
//                    )
//            ).forEach(studentService::createStudent);
//
//            List.of(
//                    new Course(
//                            "aaa",
//                            "ppp",
//                            "wdefsvfd",
//                            7
//                    ),
//                    new Course(
//                            "bbb",
//                            "ppp",
//                            "wdefsvfd",
//                            7
//                    ),
//                    new Course(
//                            "qqq",
//                            "ppp",
//                            "wdefsvfd",
//                            7
//                    ),
//                    new Course(
//                            "www",
//                            "ppp",
//                            "wdefsvfd",
//                            7
//                    )
//            ).forEach(courseService::createCourse);
//
//            List.of(
//                    new Book(
//                            "nnn",
//                            "mmm",
//                            "mmm",
//                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
//                            "qwfewrf"
//                    ),
//                    new Book(
//                            "nnn",
//                            "qqq",
//                            "mmm",
//                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
//                            "qwfewrf"
//                    )
//            ).forEach(bookService::createBook);
//
//            universityService.createUniversity(new University(
//                    "uuu",
//                    "uuu",
//                    "uuu"
//            ));
//
//            courseService.assignBooksToCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    bookService.getBooksByName("nnn")
//            );
//
//            courseService.assignStudentsToCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    studentService.getAllStudents()
//            );
//
//
//            courseService.assignCoursePreRequisiteCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    courseService.getCoursesByNameAndDepartment("bbb", "ppp")
//            );
//
//            courseService.assignCoursePreRequisiteCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    courseService.getCoursesByNameAndDepartment("qqq", "ppp")
//            );
//
//            courseService.assignCoursePreRequisiteCourse(
//                    courseService.getCoursesByNameAndDepartment("qqq", "ppp"),
//                    courseService.getCoursesByNameAndDepartment("www", "ppp")
//            );
//
//            courseService.assignCoursePreRequisiteCourse(
//                    courseService.getCoursesByNameAndDepartment("www", "ppp"),
//                    courseService.getCoursesByNameAndDepartment("bbb", "ppp")
//            );
//
//            courseService.removeCoursePreRequisiteFromCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    courseService.getCoursesByNameAndDepartment("bbb", "ppp")
//            );
//
//
//            courseService.assignUniversityToCourse(
//                    courseService.getCoursesByNameAndDepartment("aaa", "ppp"),
//                    universityService.getUniversityById(1L)
//            );
//            studentService.assignUniversityToStudent(
//                    studentService.getStudentById(1L),
//                    universityService.getUniversityById(1L)
//            );
//        };
//    }

    private static void enableJmsFlow(KafkaService kafkaService, CourseService courseService) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    courseService.createCourse(NewModel.createRandomCourse());
                },
                0, 5, TimeUnit.SECONDS);
    }
}
