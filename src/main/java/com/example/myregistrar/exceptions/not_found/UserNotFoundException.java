package com.example.myregistrar.exceptions.not_found;

public class UserNotFoundException extends ModelNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
