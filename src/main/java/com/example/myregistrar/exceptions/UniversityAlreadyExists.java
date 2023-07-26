package com.example.myregistrar.exceptions;

public class UniversityAlreadyExists extends RuntimeException {
    public UniversityAlreadyExists(String message) {
        super(message);
    }
}
