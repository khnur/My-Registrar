package com.example.myregistrar.exceptions.not_found;

public class CourseNotFoundException extends ModelNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
