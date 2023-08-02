package com.example.myregistrar.exceptions.conflict;

public class ModelAlreadyExistsException extends RuntimeException {
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
