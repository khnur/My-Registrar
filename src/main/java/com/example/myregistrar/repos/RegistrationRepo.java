package com.example.myregistrar.repos;

import com.example.myregistrar.embeddables.RegistrationId;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Registration;
import com.example.myregistrar.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, RegistrationId> {
    @Query("SELECT r.course FROM Registration r WHERE r.student = :student")
    List<Course> findCoursesByStudent(@Param("student") Student student);

    @Query("select r.student from Registration r where r.course = :course")
    List<Student> findStudentsByCourse(@Param("course") Course course);
}
