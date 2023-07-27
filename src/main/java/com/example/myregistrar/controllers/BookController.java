package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return BookMapper.INSTANCE.bookToBookDto(
                bookService.createBook(bookDto.toBook())
        );
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getAllBooks()
        );
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return BookMapper.INSTANCE.bookToBookDto(
                bookService.getBookById(id)
        );
    }
}
