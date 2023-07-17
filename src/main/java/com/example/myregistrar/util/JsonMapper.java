package com.example.myregistrar.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setDateFormat(DateMapper.DATE_FORMAT);
    }

    public static String toJsonString(Object object) {
        try {
            return "\n" + object.getClass().getSimpleName() + " " + mapper.writeValueAsString(object) + "\n";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return object.toString();
        }
    }

    private JsonMapper() {
        throw new IllegalStateException("JsonMapper class created");
    }
}
