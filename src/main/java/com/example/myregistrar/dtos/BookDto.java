package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.util.ConsoleInput;
import com.example.myregistrar.util.DateMapper;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Data
@NoArgsConstructor
@Slf4j
public class BookDto {
    private Long id;
    private String name;
    private String author;
    private String genre;
    private Date publishedDate;
    private String publisher;

    public BookDto(String name, String author, String genre, Date publishedDate, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

    public Book toBook() {
        return BookMapper.INSTANCE.bookDtoToBook(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

    public static BookDto getInstanceDto() throws IOException {
        log.info("Name: ");
        String name = ConsoleInput.readLine();

        log.info("Author: ");
        String author = ConsoleInput.readLine();

        log.info("Genre: ");
        String genre = ConsoleInput.readLine();

        Date publishedDate = null;
        while (publishedDate == null) {
            log.info("Published Date (" + DateMapper.PATTERN + "): ");
            try {
                publishedDate = DateMapper.DATE_FORMAT.parse(ConsoleInput.readLine());
            } catch (Exception e) {
                log.error("Entered incorrect for of date. Try again\n");
            }
        }

        log.info("Publisher: ");
        String publisher = ConsoleInput.readLine();

        return new BookDto(name, author, genre, publishedDate, publisher);
    }

    public static BookDto createRandomBookDto() {
        Faker faker = Faker.instance();
        com.github.javafaker.Book book = faker.book();

        String name = book.title();
        String author = book.author();
        String genre = book.genre();
        Date publishedDate = faker.date().birthday();
        String publisher = book.publisher();

        return new BookDto(name, author, genre, publishedDate, publisher);
    }
}
