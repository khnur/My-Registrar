package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.services.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    CourseService courseService;

    @Test
    void testGetCourseById() {
        Long id = 1L;
        when(courseService.getCourseById(id)).thenReturn(new Course());

        ResponseEntity<CourseDto> response = restTemplate
                .getForEntity("http://localhost:" + port + "/course/" + id, CourseDto.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}