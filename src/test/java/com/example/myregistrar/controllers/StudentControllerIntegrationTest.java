package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    StudentService studentService;

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(anyLong())).thenReturn(new Student());
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/1", ErrorDto.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatus());
    }
}
