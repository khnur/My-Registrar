package com.example.myregistrar.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonMapper {
    private static final String PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setDateFormat(DATE_FORMAT);
    }

    public static String toJsonString(Object entity) {
        if (entity == null) {
            log.error("Entity object is null");
            return "null";
        }

        try {
            return "\n" + entity.getClass().getSimpleName() + " " + mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return entity.toString();
        }
    }
}
