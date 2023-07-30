package com.example.myregistrar.controllers.facade;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookFacade {
    private final BookService bookService;

    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        return BookMapper.INSTANCE.bookToBookDto(
                bookService.createBook(book)
        );
    }

    public List<BookDto> getAllBooks() {
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getAllBooks()
        );
    }

    public BookDto getBookById(Long id) {
        return BookMapper.INSTANCE.bookToBookDto(
                bookService.getBookById(id)
        );
    }
}
