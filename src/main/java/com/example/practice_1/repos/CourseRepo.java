package com.example.practice_1.repos;

import com.example.practice_1.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Long> {
}
