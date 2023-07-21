package com.example.myregistrar.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class JsonMapperTest {
    @Mock
    ObjectMapper mapper;
    @InjectMocks
    JsonMapper jsonMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToJsonString() throws Exception {
        String result = JsonMapper.toJsonString("object");
        Assert.assertEquals("\nString\"object\"\n", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme