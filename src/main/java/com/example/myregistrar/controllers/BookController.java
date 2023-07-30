package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookFacade bookFacade;

    @PostMapping
    public ResponseDto<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
        BookDto newBookDto = bookFacade.createBook(bookDto);

        if (newBookDto == null) {
            return new ResponseDto<>(
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
        }
        return new ResponseDto<>(
                OK.value(),
                OK.getReasonPhrase(),
                newBookDto
        );
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
