package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Student;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer age;
    private String gender;
    private String email;
    private String password;
    private String role;

    private UniversityDto university;

    public Student toStudent() {
        return StudentMapper.INSTANCE.studentDtoToStudent(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }
}
