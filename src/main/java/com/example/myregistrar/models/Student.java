package com.example.myregistrar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Student {
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

    @Temporal(TemporalType.DATE)
    @Column(
            name = "birth_date",
            nullable = false
    )
    private LocalDate birthDate;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String gender;

    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @ManyToOne
    @JoinColumn(
            name = "university_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "university_student_fk")
    )
    private University university;

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

    public Student(String firstName, String lastName, LocalDate birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
        this.gender = gender;
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase() + "@onelab.kz";
    }
}
