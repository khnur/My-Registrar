package com.example.practice_1.services;

import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Student;
import com.example.practice_1.repos.BookRepo;
import com.example.practice_1.repos.CourseRepo;
import com.example.practice_1.repos.StudentRepo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final BookRepo bookRepo;

    public Student createStudent(Student student) throws RuntimeException {
        if (studentRepo.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with such email already exists");
        }
        return studentRepo.save(student);
    }

    public void createRandomStudents(int n) {
        for (int i = 0; i < n; ) {
            try {
                createStudent(Student.createRandomStudent());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public Course createCourse(Course course) throws RuntimeException {
        if (courseRepo.existsByNameAndUniversity(course.getName(), course.getUniversity())) {
            throw new RuntimeException("Course with such name and university already exists");
        }
        return courseRepo.save(course);
    }

    public void createRandomCourses(int n) {
        for (int i = 0; i < n; ) {
            try {
                createCourse(Course.createRandomCourse());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public Book createBook(Book book) throws RuntimeException {
        if (bookRepo.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new RuntimeException("Book with such name and author already exists");
        }
        return bookRepo.save(book);
    }

    public void createRandomBooks(int n) {
        for (int i = 0; i < n; ) {
            try {
                createBook(Book.createRandomBook());
                i++;
            } catch (Exception ignored) {
            }
        }
    }

    public List<Student> getAllStudents() throws RuntimeException {
        List<Student> studentList = studentRepo.findAll();
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student");
        }
        return studentList;
    }

    public List<Course> getAllCourses() throws RuntimeException {
        List<Course> courseList = courseRepo.findAll();
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course");
        }
        return courseList;
    }

    public List<Book> getAllBooks() throws RuntimeException {
        List<Book> bookList = bookRepo.findAll();
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book");
        }
        return bookList;
    }

    public List<Student> getStudentsByFirstName(String firstName) throws RuntimeException {
        List<Student> studentList = studentRepo.findStudentsByFirstName(firstName);
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student with such first name");
        }
        return studentList;
    }

    public List<Course> getCoursesByName(String name) throws RuntimeException {
        List<Course> courseList = courseRepo.findCoursesByName(name);
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course with such name");
        }
        return courseList;
    }

    public List<Book> getBooksByName(String name) throws RuntimeException {
        List<Book> bookList = bookRepo.findBooksByName(name);
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book with such name");
        }
        return bookList;
    }

    public List<Student> getStudentsByLastName(String lastName) throws RuntimeException {
        List<Student> studentList = studentRepo.findStudentsByLastName(lastName);
        if (studentList.isEmpty()) {
            throw new RuntimeException("There is no student with such last name");
        }
        return studentList;
    }

    public List<Course> getCoursesByUniversity(String university) throws RuntimeException {
        List<Course> courseList = courseRepo.findCoursesByUniversity(university);
        if (courseList.isEmpty()) {
            throw new RuntimeException("There is no course with such university");
        }
        return courseList;
    }

    public List<Book> getBooksByAuthor(String author) throws RuntimeException {
        List<Book> bookList = bookRepo.findBooksByAuthor(author);
        if (bookList.isEmpty()) {
            throw new RuntimeException("There is no book with such author");
        }
        return bookList;
    }

    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) throws RuntimeException {
        return studentRepo.findStudentByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("There is no student with such first and last name"));
    }

    public Course getCourseByNameAndUniversity(String name, String university) throws RuntimeException {
        return courseRepo.findCourseByNameAndUniversity(name, university)
                .orElseThrow(() -> new RuntimeException("There is no course with such name and university"));
    }

    public Book getBookByNameAndAuthor(String name, String author) throws RuntimeException {
        return bookRepo.findBookByNameAndAuthor(name, author)
                .orElseThrow(() -> new RuntimeException("There is no book with such name and author"));
    }


}
