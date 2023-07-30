package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.JwtDto;
import com.example.myregistrar.dtos.auth_dto.UserDto;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void testLogin() {
        JwtDto jwtDto = new JwtDto();
        UserDto userDto = new UserDto("admin", "admin");
        ResponseEntity<JwtDto> response = restTemplate
                .postForEntity("http://localhost:" + port + "/auth/login", jwtDto, JwtDto.class, userDto);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRegister() {
        UserDto userDto = new UserDto("admin", "admin");
        ResponseEntity<UserDto> response = restTemplate
                .postForEntity("http://localhost:" + port + "/auth/register", userDto, UserDto.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

