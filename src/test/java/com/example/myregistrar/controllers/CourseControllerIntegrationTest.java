package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.CourseDto;
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
class CourseControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateCourse() {
        Course course = new Course("name", "department", "instructor", 0);

        ResponseEntity<CourseDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/course", course.toCourseDto(), CourseDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}