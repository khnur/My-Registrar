package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.example.myregistrar.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({
            StudentNotFoundException.class,
            CourseNotFoundException.class,
            BookNotFoundException.class,
            UniversityNotFoundException.class
    })
    public ResponseEntity<ErrorDto> handleNotFound(HttpServletRequest httpServletRequest, RuntimeException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
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
    public ResponseEntity<ErrorDto> handleConflict(HttpServletRequest httpServletRequest, RuntimeException e) {
        return ResponseEntity.status(CONFLICT).body(
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(CONFLICT.value())
                        .error(CONFLICT.getReasonPhrase())
                        .path(httpServletRequest.getServletPath())
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidation(HttpServletRequest httpServletRequest, RuntimeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .path(httpServletRequest.getServletPath())
                        .message(e.getMessage())
                        .build()
        );
    }
}

