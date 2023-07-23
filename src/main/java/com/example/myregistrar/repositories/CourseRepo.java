package com.example.myregistrar.repositories;

import com.example.myregistrar.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    boolean existsByNameAndUniversity(String name, String university);
    List<Course> findCoursesByName(String name);
    List<Course> findCoursesByUniversity(String university);
    Optional<Course> findCourseByNameAndUniversity(String name, String university);

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
}
