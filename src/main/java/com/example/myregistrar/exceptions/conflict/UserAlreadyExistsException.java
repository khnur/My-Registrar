package com.example.myregistrar.exceptions.conflict;

public class UserAlreadyExistsException extends ModelAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
