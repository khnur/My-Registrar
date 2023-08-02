package com.example.myregistrar.exceptions.conflict;

public class UniversityAlreadyExistsException extends ModelAlreadyExistsException {
    public UniversityAlreadyExistsException(String message) {
        super(message);
    }
}
