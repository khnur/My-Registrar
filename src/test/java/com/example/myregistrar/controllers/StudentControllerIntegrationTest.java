package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.ErrorDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateStudent_Success() {
        Student student= new Student("Nurzhan", "kkk", new Date(), "password", "aaa", "USER", true);

        ResponseEntity<StudentDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/student", student.toStudentDto(), StudentDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Nurzhan", response.getBody().getFirstName());
        Assertions.assertEquals("kkk", response.getBody().getLastName());
    }

    @Test
    void testGetStudentById() {
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/1", ErrorDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(0, response.getBody().getStatus());
    }
}
