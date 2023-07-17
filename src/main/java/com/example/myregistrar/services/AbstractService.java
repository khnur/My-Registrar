package com.example.myregistrar.services;

import com.example.myregistrar.tables.CourseTable;
import com.example.myregistrar.tables.StudentTable;
import com.example.myregistrar.tables.BookTable;

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
