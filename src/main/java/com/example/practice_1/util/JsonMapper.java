package com.example.practice_1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String toJsonString(Object object) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return "\n" + object.getClass().getSimpleName() + " " + mapper.writeValueAsString(object) + "\n";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return object.toString();
        }
    }
}
