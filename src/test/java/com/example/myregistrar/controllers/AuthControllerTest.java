package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.LoginDto;
import com.example.myregistrar.security.JwtService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestConstructor;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;

    @LocalServerPort
    private int port;
    @InjectMocks
    AuthController authController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(jwtService.generateToken(anyString())).thenReturn("generateTokenResponse");

        String result = authController.login(new LoginDto());
        Assertions.assertEquals(null, result);
    }


    @Test
    void testLogin_ValidCredentials() {
        String username = "one.lab";
        String password = "password";

        LoginDto loginDto = new LoginDto(username, password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/login", loginDto, String.class);

        Assertions.assertNotEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testLogin_InvalidCredentials() {
        String username = "one.lab";
        String password = "wrong_password";

        LoginDto loginDto = new LoginDto(username, password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/login", loginDto, String.class);

        Assertions.assertNotEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
