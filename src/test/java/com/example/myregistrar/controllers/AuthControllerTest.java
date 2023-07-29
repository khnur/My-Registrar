package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.LoginDto;
import com.example.myregistrar.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @LocalServerPort
    private int port;
    @InjectMocks
    AuthController authController;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testAuthSuccessful() throws Exception {
        String email = "test@example.com";
        String password = "testPassword";

        UserDetails userDetails = new CustomUserDetails(email, password);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("testToken");

        LoginDto loginDto = new LoginDto(email, password);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }


    @Test
    void testLogin_ValidCredentials() {
        String username = "one.lab";
        String password = "password";

        LoginDto loginDto = new LoginDto(username, password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth", loginDto, String.class);

        Assertions.assertNotEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testLogin_InvalidCredentials() {
        String username = "one.lab";
        String password = "wrong_password";

        LoginDto loginDto = new LoginDto(username, password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth", loginDto, String.class);

        Assertions.assertNotEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    private static class CustomUserDetails implements UserDetails {
        private final String email;
        private final String password;

        public CustomUserDetails(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
