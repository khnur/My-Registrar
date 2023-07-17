package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {
    public static <T> RowMapper<T> getRowMapper(Class<?> clazz) {
        if (Student.class.isAssignableFrom(clazz)) {
            return (RowMapper<T>) new StudentMapper();
        } else if (Course.class.isAssignableFrom(clazz)) {
            return (RowMapper<T>) new CourseMapper();
        } else if (Book.class.isAssignableFrom(clazz)) {
            return (RowMapper<T>) new BookMapper();
        } else {
            throw new UnsupportedOperationException("Unsupported type: " + clazz.getName());
        }
    }

    private static class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();

            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("firstName"));
            student.setLastName(rs.getString("lastName"));
            student.setBirthDate(rs.getDate("birthDate"));
            student.setAge(rs.getInt("age"));
            student.setGender(rs.getString("gender"));
            student.setEmail(rs.getString("email"));

            return student;
        }
    }

    private static class CourseMapper implements RowMapper<Course> {
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();

            course.setId(rs.getLong("id"));
            course.setName(rs.getString("name"));
            course.setUniversity(rs.getString("university"));
            course.setDepartment(rs.getString("department"));
            course.setInstructor(rs.getString("instructor"));
            course.setCreditHours(rs.getInt("creditHours"));

            return course;
        }
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();

            book.setId(rs.getLong("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setGenre(rs.getString("genre"));
            book.setPublishedDate(rs.getDate("publishedDate"));
            book.setPublisher(rs.getString("publisher"));

            return book;
        }
    }


    private EntityMapper() {
        throw new IllegalStateException("EntityMapper class created");
    }
}
