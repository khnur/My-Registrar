package com.example.myregistrar.repositories;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.CoursePreRequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePreRequiteRepo extends JpaRepository<CoursePreRequisite, CoursePreRequisiteId> {
    @Query("SELECT c.coursePreReq FROM CoursePreRequisite c WHERE c.course.id = :courseId")
    List<Course> findPrerequisiteCoursesByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Query("DELETE FROM CoursePreRequisite c WHERE c.course.id = ?1 and c.coursePreReq.id = ?2")
    void deleteByCourseIdAndCoursePreReqId(Long courseId, Long coursePreReqId);
}
