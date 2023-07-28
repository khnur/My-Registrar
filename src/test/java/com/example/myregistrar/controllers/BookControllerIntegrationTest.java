package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateBook() {
        Book book = new Book("name", "author", "genre", new GregorianCalendar(2023, Calendar.JULY, 28, 21, 48).getTime(), "publisher", 0);

        ResponseEntity<BookDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/book", book.toBookDto(), BookDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllBooks() {
        ResponseEntity<List<BookDto>> response = restTemplate.exchange(
                "http://localhost:" + port + "/book", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BookDto>>() {});

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        ResponseEntity<BookDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/book/" + bookId, BookDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}