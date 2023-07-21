package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "book.course", target = "courseDto")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "bookDto.courseDto", target = "course")
    Book bookDtoToBook(BookDto bookDto);
    List<BookDto> bookListToBookDtoList(List<Book> bookList);
    List<Book> bookDtoListToBookList(List<BookDto> bookDtoList);
}
