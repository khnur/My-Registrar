package com.example.practice_1;

import com.example.practice_1.services.Service;
import com.example.practice_1.util.CLI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CLI cli) {
        return args -> {
            cli.init();
        };
    }

}
