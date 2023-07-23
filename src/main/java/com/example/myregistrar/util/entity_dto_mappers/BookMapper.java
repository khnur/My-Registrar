package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
    List<BookDto> bookListToBookDtoList(List<Book> bookList);
    List<Book> bookDtoListToBookList(List<BookDto> bookDtoList);
}
