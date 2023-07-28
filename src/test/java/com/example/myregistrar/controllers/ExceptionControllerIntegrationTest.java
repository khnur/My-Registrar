package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.ErrorDto;
import com.example.myregistrar.models.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExceptionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHandleStudentNotFoundException() {
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/students/1", ErrorDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        Assertions.assertEquals("Not Found", response.getBody().getError());
        Assertions.assertNotNull(response.getBody().getTimestamp());
        Assertions.assertNotNull(response.getBody().getMessage());
        Assertions.assertNotNull(response.getBody().getPath());
    }

    @Test
    void testHandleCourseAlreadyExistsException() {
        Course course = new Course("name", "department", "instructor", 0);
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/course", course.toCourseDto(), ErrorDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(0, response.getBody().getStatus());
    }
}
