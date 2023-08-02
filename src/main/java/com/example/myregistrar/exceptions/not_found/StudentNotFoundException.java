package com.example.myregistrar.exceptions.not_found;

public class StudentNotFoundException extends ModelNotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
