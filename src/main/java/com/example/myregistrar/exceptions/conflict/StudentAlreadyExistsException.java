package com.example.myregistrar.exceptions.conflict;

public class StudentAlreadyExistsException extends ModelAlreadyExistsException {
    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
