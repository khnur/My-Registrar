package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.ErrorDto;
import com.example.myregistrar.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({
            StudentNotFoundException.class,
            CourseNotFoundException.class,
            BookNotFoundException.class,
            UniversityNotFoundException.class
    })
    public ResponseEntity<?> handleNotFound(HttpServletRequest httpServletRequest, RuntimeException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                ErrorDto.builder()
                        .timestamp(new Date())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .path(httpServletRequest.getServletPath())
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({
            StudentAlreadyExistsException.class,
            CourseAlreadyExistsException.class,
            BookAlreadyExistsException.class,
            UniversityAlreadyExistsException.class
    })
    public ResponseEntity<?> handleConflict(HttpServletRequest httpServletRequest, RuntimeException e) {
        return ResponseEntity.status(CONFLICT).body(
                ErrorDto.builder()
                        .timestamp(new Date())
                        .status(CONFLICT.value())
                        .error(CONFLICT.getReasonPhrase())
                        .path(httpServletRequest.getServletPath())
                        .message(e.getMessage())
                        .build()
        );
    }
}

