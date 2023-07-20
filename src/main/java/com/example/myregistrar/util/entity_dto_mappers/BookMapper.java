package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.util.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "book.publishedDate", target = "publishedDate", dateFormat = DateMapper.PATTERN)
    BookDto bookToBookDto(Book book);

    @Mapping(source = "bookDto.publishedDate", target = "publishedDate", dateFormat = DateMapper.PATTERN)
    Book bookDtoToBook(BookDto bookDto);
    List<BookDto> bookListToBookDtoList(List<Book> bookList);
    List<Book> bookDtoListToBookList(List<BookDto> bookDtoList);
}
