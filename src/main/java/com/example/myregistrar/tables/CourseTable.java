package com.example.myregistrar.tables;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Registration;
import com.example.myregistrar.models.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseTable extends AbstractTable<Course> {

    protected CourseTable() {
        super(Course.class);
    }

    public boolean existsByNameAndUniversity(String name, String university) {
        return super.existsByFirstAndSecond(name, university);
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
        super.save(course, course.getName(), course.getUniversity());
    }
}
