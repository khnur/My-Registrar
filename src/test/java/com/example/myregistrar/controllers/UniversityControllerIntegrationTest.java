package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.UniversityService;
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
class UniversityControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    UniversityService universityService;

    @Test
    void testGetUniversityById() {
        Long id = 1L;
        when(universityService.getUniversityById(id)).thenReturn(new University());

        ResponseEntity<UniversityDto> response = restTemplate
                .getForEntity("http://localhost:" + port + "/university/" + id, UniversityDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

