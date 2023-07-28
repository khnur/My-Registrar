package com.example.myregistrar.util;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.models.University;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

class NewModelTest {
    @Mock
    Faker faker;
    @Mock
    Logger log;
    @InjectMocks
    NewModel newModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreateRandomCourse() {
        Course result = NewModel.createRandomCourse();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertNotNull(result.getDepartment());
        Assertions.assertNotNull(result.getInstructor());
        Assertions.assertNotNull(result.getCreditHours());
    }

    @Test
    void testCreateRandomBook() {
        Book result = NewModel.createRandomBook();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertNotNull(result.getAuthor());
        Assertions.assertNotNull(result.getGenre());
        Assertions.assertNotNull(result.getPublishedDate());
        Assertions.assertNotNull(result.getPublisher());
        Assertions.assertNotNull(result.getPageNumber());
    }

    @Test
    void testCreateRandomStudent() {
        Student result = NewModel.createRandomStudent();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getFirstName());
        Assertions.assertNotNull(result.getLastName());
        Assertions.assertNotNull(result.getBirthDate());
        Assertions.assertNotNull(result.getGender());
        Assertions.assertNotNull(result.getPassword());
        Assertions.assertNotNull(result.getRole());
        Assertions.assertNotNull(result.isActive());
    }

    @Test
    void testCreateRandomUniversity() {
        University result = NewModel.createRandomUniversity();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertNotNull(result.getCountry());
        Assertions.assertNotNull(result.getCity());
    }
}
