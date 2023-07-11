package com.example.practice_1;

import com.example.practice_1.models.Student;
import com.example.practice_1.repos.StudentRepo;
import com.example.practice_1.util.JsonMapper;
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
	CommandLineRunner commandLineRunner(
			StudentRepo studentRepo
	) {
		return args -> {
			for (int i = 0; i < 3; i++) {
				studentRepo.save(Student.createRandomStudent());
			}
			studentRepo.findAll().forEach(student -> System.out.println(JsonMapper.toJsonString(student)));
		};
	}

}
