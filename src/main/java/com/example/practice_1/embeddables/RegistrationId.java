package com.example.practice_1.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RegistrationId implements Serializable {
    private Long studentId;

    private Long courseId;

    public RegistrationId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
