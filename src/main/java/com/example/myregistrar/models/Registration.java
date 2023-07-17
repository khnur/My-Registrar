package com.example.myregistrar.models;

import com.example.myregistrar.embeddables.RegistrationId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Registration {
    private RegistrationId id;

    private Student student;

    private Course course;

    private LocalDateTime registeredTime;

    public Registration(RegistrationId id, Student student, Course course, LocalDateTime registeredTime) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.registeredTime = registeredTime;
    }
}
