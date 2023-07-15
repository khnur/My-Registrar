package com.example.practice_1.services;

import com.example.practice_1.tables.BookTable;
import com.example.practice_1.tables.CourseTable;
import com.example.practice_1.tables.StudentTable;

public abstract class AbstractService {
    protected final StudentTable studentData;
    protected final CourseTable courseData;
    protected final BookTable bookData;

    protected AbstractService(StudentTable studentData, CourseTable courseData, BookTable bookData) {
        this.studentData = studentData;
        this.courseData = courseData;
        this.bookData = bookData;
    }
}
