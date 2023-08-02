package com.example.myregistrar.exceptions.not_found;

public class BookNotFoundException extends ModelNotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
