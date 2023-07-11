package com.example.practice_1.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RegistrationId {
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    public RegistrationId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
