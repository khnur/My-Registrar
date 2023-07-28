package com.example.myregistrar.models;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String department;

    @Column
    private String instructor;

    @Column(
            name = "credit_hours",
            nullable = false
    )
    private Integer creditHours;

    @ManyToOne
    @JoinColumn(
            name = "university_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "university_course_fk")
    )
    private University university;

    @ManyToMany(
            mappedBy = "courses",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            }
    )
    private List<Student> students = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Book> books = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CoursePreRequisite> coursePreRequisiteList = new ArrayList<>();

    public CourseDto toCourseDto() {
        return CourseMapper.INSTANCE.courseToCourseDto(this);
    }

    public Course(String name, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }
}
