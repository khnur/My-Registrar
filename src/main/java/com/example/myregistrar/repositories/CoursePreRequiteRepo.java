package com.example.myregistrar.repositories;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.CoursePreRequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePreRequiteRepo extends JpaRepository<CoursePreRequisite, CoursePreRequisiteId> {
    @Query("SELECT c.coursePreReq FROM CoursePreRequisite c WHERE c.course = ?1")
    List<Course> findPrerequisiteCoursesByCourse(Course course);
}
