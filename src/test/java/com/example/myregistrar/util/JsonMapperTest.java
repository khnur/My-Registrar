package com.example.myregistrar.util;

import com.example.myregistrar.dtos.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class JsonMapperTest {
    @Mock
    ObjectMapper mapper;
    @Mock
    Logger log;
    @InjectMocks
    JsonMapper jsonMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToJsonString() throws JsonProcessingException {
        LoginDto loginDto = new LoginDto();
        String expectedResult = """
                
                LoginDto {
                  "login" : null,
                  "password" : null
                }""";


        String result = JsonMapper.toJsonString(loginDto);

        Assertions.assertFalse(expectedResult.equals(result));
    }

    @Test
    void testToJsonStringNullEntity() {
        String expectedResult = "null";

        String result = JsonMapper.toJsonString(null);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testToJsonStringException() throws JsonProcessingException {
        Object entity = new LoginDto();

        when(mapper.writeValueAsString(entity)).thenThrow(new RuntimeException("Test exception"));

        Assertions.assertDoesNotThrow(() -> JsonMapper.toJsonString(entity));
    }
}