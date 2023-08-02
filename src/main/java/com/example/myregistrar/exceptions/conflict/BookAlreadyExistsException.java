package com.example.myregistrar.exceptions.conflict;

public class BookAlreadyExistsException extends ModelAlreadyExistsException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
