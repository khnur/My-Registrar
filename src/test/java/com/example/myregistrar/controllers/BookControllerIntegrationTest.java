package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    BookService bookService;

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        when(bookService.getBookById(bookId)).thenReturn(new Book());

        ResponseEntity<BookDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/book/" + bookId, BookDto.class);


        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}