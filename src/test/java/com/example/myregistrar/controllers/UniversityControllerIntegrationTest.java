package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.ErrorDto;
import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.models.University;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UniversityControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateUniversity_Success() {
        University university = new University("name", "country", "city");

        ResponseEntity<UniversityDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/university", UniversityMapper.INSTANCE.universityToUniversityDto(university), UniversityDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("name", response.getBody().getName());
        Assertions.assertEquals("country", response.getBody().getCountry());
    }
}

