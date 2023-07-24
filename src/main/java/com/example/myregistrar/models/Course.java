package com.example.myregistrar.models;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "course_index")
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

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String department;

    @Column
    private String instructor;

    @Column(
            name = "credit_hours",
            nullable = false
    )
    private Integer creditHours;

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

    public Course(String name, String university, String department, String instructor, Integer creditHours) {
        this.name = name;
        this.university = university;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }

    public CourseDto toCourseDto() {
        return CourseMapper.INSTANCE.courseToCourseDto(this);
    }
}
