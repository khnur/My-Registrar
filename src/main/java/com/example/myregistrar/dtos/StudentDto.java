package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

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
    private String universityName;

    @ToString.Exclude
    @JsonIgnore
    private University university;

    public Student toStudent() {
        return StudentMapper.INSTANCE.studentDtoToStudent(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public void setUniversity(University university) {
        this.university = university;
        this.universityName = university != null ? university.getName() : null;
    }

}
