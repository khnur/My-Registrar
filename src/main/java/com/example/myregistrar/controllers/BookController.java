package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.dtos.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookFacade bookFacade;

    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookFacade.createBook(bookDto);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookFacade.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookFacade.getBookById(id);
    }
}
