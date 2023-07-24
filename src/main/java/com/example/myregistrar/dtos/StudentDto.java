package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Student;
import com.example.myregistrar.util.ConsoleInput;
import com.example.myregistrar.util.DateMapper;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Data
@NoArgsConstructor
@Slf4j
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer age;
    private String gender;
    private String email;

    public StudentDto(String firstName, String lastName, Date birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = DateMapper.GET_AGE(birthDate);
        this.gender = gender;
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz";
    }

    public Student toStudent() {
        return StudentMapper.INSTANCE.studentDtoToStudent(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public static StudentDto getInstanceDto() throws IOException {
        log.info("First Name: ");
        String firstName = ConsoleInput.readLine();

        log.info("Last Name: ");
        String lastName = ConsoleInput.readLine();

        Date birthDate = null;
        while (birthDate == null) {
            log.info("Birth Date (" + DateMapper.PATTERN + "): ");
            try {
                birthDate = DateMapper.DATE_FORMAT.parse(ConsoleInput.readLine());
            } catch (Exception e) {
                log.error("Entered incorrect for of date. Try again\n");
            }
        }

        String gender = null;

        while (gender == null) {
            log.info("Gender: ");
            gender = ConsoleInput.readLine();

            if (gender.startsWith("M") || gender.startsWith("m")) {
                gender = "Male";
            } else if (gender.startsWith("F") || gender.startsWith("f")) {
                gender = "Female";
            } else {
                gender = null;
                log.error("Entered invalid format of gender. Try again\n");
            }
        }

        return new StudentDto(firstName, lastName, birthDate, gender);
    }

    public static StudentDto createRandomStudentDto() {
        Faker faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        String gender = faker.random().nextInt(5) % 2 == 0 ? "Male" : "Female";

        return new StudentDto(firstName, lastName, date, gender);
    }
}
