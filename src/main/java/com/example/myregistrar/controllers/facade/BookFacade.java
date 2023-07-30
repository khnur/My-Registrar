package com.example.myregistrar.controllers.facade;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.exceptions.BookNotFoundException;
import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.entity_dto_mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookFacade {
    private final BookService bookService;
    private final CourseService courseService;
    private final StudentService studentService;

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

    public List<BookDto> getBooksByCourse(Long id) {
        Course course = courseService.getCourseById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByCourse(course)
        );
    }

    public BookDto assignBookToCourse(Long id, BookDto bookDto) {
        if (bookDto == null || bookDto.getId() == null) {
            throw new BookNotFoundException("Provided transient book and it is not registered");
        }
        Course course = courseService.getCourseById(id);
        Book book = bookService.getBookById(bookDto.getId());

        courseService.assignBookToCourse(course, book);

        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    public List<BookDto> getBooksByStudentId(Long id) {
        Student student = studentService.getStudentById(id);
        return BookMapper.INSTANCE.bookListToBookDtoList(
                bookService.getBooksByStudent(student)
        );
    }
}
