package com.example.myregistrar.models;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePreRequisite {
    @EmbeddedId
    private CoursePreRequisiteId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id"
    )
    private Course course;

    @ManyToOne
    @MapsId("coursePreReqId")
    @JoinColumn(
            name = "course_pre_requisite_id",
            referencedColumnName = "id"
    )
    private Course coursePreReq;
}
