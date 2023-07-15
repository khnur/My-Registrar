package com.example.practice_1.tables;

import com.example.practice_1.models.Course;
import com.example.practice_1.models.Registration;
import com.example.practice_1.models.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseTable extends AbstractTable<Course> {

    public boolean existsByNameAndUniversity(String name, String university) {
        return super.existsByFistAndSecond(name, university);
    }

    public List<Course> findCoursesByName(String name) {
        return super.findListByFirst(name);
    }

    public List<Course> findCoursesByUniversity(String university) {
        return super.findListBySecond(university);
    }

    public Optional<Course> findCourseByNameAndUniversity(String name, String university) {
        return super.findByFirstAndSecond(name, university);
    }

    public List<Course> findCoursesByStudent(Student student) {
        return student.getRegistrations().stream()
                .map(Registration::getCourse)
                .toList();
    }

    public void save(Course course) {
        map.put(toHash(course.getName(), course.getUniversity()), course);
    }
}
