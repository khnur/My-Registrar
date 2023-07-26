package com.example.myregistrar.repositories;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    boolean existsByNameAndUniversityId(String name, Long universityId);
    List<Course> findCoursesByName(String name);
    List<Course> findCoursesByUniversityId(Long universityId);

    Optional<Course> findCourseByNameAndDepartment(String name, String department);
    Optional<Course> findCourseByNameAndUniversityId(String name, Long universityId);

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
}
