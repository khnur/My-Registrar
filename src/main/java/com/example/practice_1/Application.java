package com.example.practice_1;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Student;
import com.example.practice_1.util.CLI;
import com.example.practice_1.util.DataBase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        Arrays.stream(Student.class.getDeclaredFields())
//                .forEach(a -> {
//                    System.out.println(a.getModifiers());
//                    System.out.println(a.getName());
//                    System.out.println(Arrays.toString(a.getAnnotations()));
//                    System.out.println(a.getType().isPrimitive());
//                    System.out.println();
//                });
    }

    @Bean
    CommandLineRunner commandLineRunner(CLI cli) {
        return args -> {
            DataBase.viewTable(Student.class);
            cli.init();
        };
    }

}
