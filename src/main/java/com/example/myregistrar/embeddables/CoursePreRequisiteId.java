package com.example.myregistrar.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePreRequisiteId implements Serializable {
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_pre_requisite_id")
    private Long coursePreReqId;
}
