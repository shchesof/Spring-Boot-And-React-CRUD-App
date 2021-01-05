package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.BookDto;
import cz.cvut.fel.sin.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = BookController.BASE_URI)
public class BookController {

    public static final String BASE_URI = "/books";
    public static final String DETAIL_SUBURI = "/{bookId}";
    public static final String DETAIL_URI = BASE_URI + DETAIL_SUBURI;

    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public BookDto createNewBook(@RequestBody BookDto bookDto) {
        return bookService.createNewBook(bookDto);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(DETAIL_SUBURI)
    public BookDto getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PutMapping(DETAIL_SUBURI)
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(DETAIL_SUBURI)
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }
}
