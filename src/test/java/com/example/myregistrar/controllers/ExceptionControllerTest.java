package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.example.myregistrar.exceptions.CourseAlreadyExistsException;
import com.example.myregistrar.exceptions.StudentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class ExceptionControllerTest {
    ExceptionController exceptionController = new ExceptionController();

    @Test
    void testHandleStudentNotFoundException() {
        StudentNotFoundException exception = new StudentNotFoundException("Student not found");
        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<?> result = exceptionController.handleNotFound(request, exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Assertions.assertEquals("Student not found", ((ErrorDto) result.getBody()).getMessage());
    }

    @Test
    void testHandleCourseAlreadyExistsException() {
        CourseAlreadyExistsException exception = new CourseAlreadyExistsException("Course already exists");
        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<?> result = exceptionController.handleConflict(request, exception);

        Assertions.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        Assertions.assertEquals("Course already exists", ((ErrorDto) result.getBody()).getMessage());
    }
}