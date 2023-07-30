package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.JwtDto;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import com.example.myregistrar.dtos.auth_dto.UserDto;
import com.example.myregistrar.models.EndUser;
import com.example.myregistrar.security.JwtService;
import com.example.myregistrar.services.EndUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;
    @Mock
    UserDetailsService userDetailsService;
    @Mock
    EndUserService endUserService;
    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAuthFurther() {
        String expectedJwt = "generateTokenResponse";
        when(jwtService.generateToken(any())).thenReturn(expectedJwt);

        UserDto userDto = new UserDto("username", "password", "role", LocalDateTime.of(2023, Month.JULY, 30, 16, 17, 10));
        ResponseDto<JwtDto> responseEntity = authController.auth(userDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatus());
    }

    @Test
    void testAuth() {
        when(jwtService.generateToken(any())).thenReturn("generateTokenResponse");

        UserDto userDto = new UserDto("username", "password", "role", LocalDateTime.of(2023, Month.JULY, 30, 16, 17, 10));
        ResponseDto<JwtDto> result = authController.auth(userDto);
        Assertions.assertEquals(new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null), result);
    }

    @Test
    void testRegister() {
        EndUser endUser = new EndUser("username", "password", "role");
        when(endUserService.createUser(any())).thenReturn(endUser);

        ResponseDto<UserDto> result = authController.register(new UserDto("username", "password", "role", LocalDateTime.now()));

        assertEquals(new ResponseDto<>(HttpStatus.OK.value(), "new user created with username=username and role=role",
                new UserDto("username", "password", "role", LocalDateTime.now())).getStatus(), result.getStatus());
    }

    @Test
    void testRegisterFurther() {
        UserDto userDto = new UserDto("username", "password", "role", LocalDateTime.of(2023, Month.JULY, 30, 16, 17, 10));
        when(endUserService.createUser(any())).thenReturn(new EndUser("username", "password", "role"));

        ResponseDto<UserDto> responseEntity = authController.register(userDto);

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatus());
    }
}