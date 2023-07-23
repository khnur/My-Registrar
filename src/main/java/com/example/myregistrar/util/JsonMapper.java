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

        Object object = entity;
        if (entity instanceof Student) {
            object = ((Student) entity).toStudentDto();
        } else if (entity instanceof Course) {
            object = ((Course) entity).toCourseDto();
        } else if (entity instanceof Book) {
            object = ((Book) entity).toBookDto();
        }

        try {
            return "\n" + entity.getClass().getSimpleName() + " " + mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return object.toString();
        }
    }

    private JsonMapper() {
        throw new IllegalStateException("JsonMapper class created");
    }
}
