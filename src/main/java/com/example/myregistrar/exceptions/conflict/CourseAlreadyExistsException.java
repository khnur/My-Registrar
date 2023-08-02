package com.example.myregistrar.exceptions.conflict;

public class CourseAlreadyExistsException extends ModelAlreadyExistsException {
    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
