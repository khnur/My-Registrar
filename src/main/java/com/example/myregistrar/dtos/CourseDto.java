package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.util.ConsoleInput;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@NoArgsConstructor
@Slf4j
public class CourseDto {
    private Long id;

    private String name;

    private String university;

    private String department;

    private String instructor;

    private Integer creditHours;

    public CourseDto(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.university = university;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }

    public Course toCourse() {
        return CourseMapper.INSTANCE.courseDtoToCourse(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public static CourseDto getInstanceDto() throws IOException {
        log.info("Name: ");
        String name = ConsoleInput.readLine();

        log.info("University: ");
        String university = ConsoleInput.readLine();

        log.info("Department: ");
        String department = ConsoleInput.readLine();

        log.info("Instructor: ");
        String instructor = ConsoleInput.readLine();

        Integer creditHours = null;
        while (creditHours == null) {
            log.info("Credit hours (ECT : 4 - 12): ");
            try {
                int temp = ConsoleInput.readInt();

                if (temp >= 4 && temp <= 12) {
                    creditHours = temp;
                } else {
                    log.error("Invalid number of credit hours entered. Try again");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return new CourseDto(name, university, department, instructor, creditHours);
    }

    public static CourseDto createRandomCourseDto() {
        Faker faker = Faker.instance();

        String name = faker.programmingLanguage().name();
        String university = faker.university().name();
        String department = faker.educator().course();
        String instructor = faker.funnyName().name();
        Integer creditHours = faker.number().numberBetween(4, 12);

        return new CourseDto(name, university, department, instructor, creditHours);
    }
}
