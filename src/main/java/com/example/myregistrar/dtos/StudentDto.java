package com.example.myregistrar.dtos;

import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer age;
    private String gender;
    private String email;
    @ToString.Exclude
    @JsonIgnore
    private List<CourseDto> courseDtoList = new ArrayList<>();

    public StudentDto(String firstName, String lastName, Date birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = DateMapper.GET_AGE(birthDate);
        this.gender = gender;
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz";
    }
}
