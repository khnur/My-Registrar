package com.example.myregistrar.models;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.util.DateMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@Slf4j
public class Student {
    private static final String DOMAIN = "@onelab.kz";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "birth_date",
            nullable = false
    )
    private Date birthDate;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String email;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            }
    )
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    private List<Course> courses = new ArrayList<>();

    public Student(String firstName, String lastName, Date birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = DateMapper.GET_AGE(birthDate);
        this.gender = gender;
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + DOMAIN;
    }

    public StudentDto toStudentDto() {
        return StudentMapper.INSTANCE.studentToStudentDto(this);
    }
}
