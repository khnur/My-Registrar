package com.example.myregistrar;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.CLI;
import com.example.myregistrar.util.DateMapper;
import com.example.myregistrar.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(
            CLI cli,
            StudentService studentService,
            CourseService courseService,
            BookService bookService
    ) {
        return args -> {
            List.of(
                    new StudentDto(
                            "aaa",
                            "bbb",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "M"
                    ),
                    new StudentDto(
                            "aaa",
                            "ppp",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "M"
                    )
            ).forEach(studentService::createStudent);

            List.of(
                    new CourseDto(
                            "aaa",
                            "bbb",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "aaa",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    )
            ).forEach(courseService::createCourse);

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
            ).forEach(bookService::createBook);

            courseService.assignBooksToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "bbb"),
                    bookService.getBooksByName("nnn")
            );

            courseService.assignStudentsToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "bbb"),
                    studentService.getAllStudents()
            );

            cli.init();
        };
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }

}
