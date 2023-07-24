package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setDateFormat(DateMapper.DATE_FORMAT);
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

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            int classNameEndIndex = json.indexOf(' ');
            if (classNameEndIndex != -1 && classNameEndIndex + 1 < json.length()) {
                json = json.substring(classNameEndIndex + 1);
            }
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error during JSON deserialization: " + e.getMessage());
            return null;
        }
    }

    private JsonMapper() {
        throw new IllegalStateException("JsonMapper class created");
    }
}
