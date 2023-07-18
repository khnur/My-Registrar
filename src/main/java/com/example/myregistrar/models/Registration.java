package com.example.myregistrar.models;

import com.example.myregistrar.embeddables.RegistrationId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class Registration {
    @EmbeddedId
    private RegistrationId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id"
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id"
    )
    private Course course;

    @Column(
            name = "registered_time",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime registeredTime;

    public Registration(RegistrationId id, Student student, Course course, LocalDateTime registeredTime) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.registeredTime = registeredTime;
    }
}
