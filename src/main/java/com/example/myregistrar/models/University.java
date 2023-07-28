package com.example.myregistrar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String country;

    @Column
    private String city;

    @OneToMany(
            mappedBy = "university",
            cascade = CascadeType.ALL
    )
    List<Student> students = new ArrayList<>();

    @OneToMany(
            mappedBy = "university",
            cascade = CascadeType.ALL
    )
    List<Course> courses = new ArrayList<>();

    public University(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }
}
