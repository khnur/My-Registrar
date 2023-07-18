package com.example.myregistrar.repos;

import com.example.myregistrar.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {
    boolean existsByNameAndUniversity(String name, String university);

    List<Course> findCoursesByName(String name);

    List<Course> findCoursesByUniversity(String university);

    Optional<Course> findCourseByNameAndUniversity(String name, String university);
}
