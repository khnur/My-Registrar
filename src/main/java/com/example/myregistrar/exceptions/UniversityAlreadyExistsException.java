package com.example.myregistrar.exceptions;

public class UniversityAlreadyExistsException extends RuntimeException {
    public UniversityAlreadyExistsException(String message) {
        super(message);
    }
}
