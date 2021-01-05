package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createNewBook(BookDto bookDto);

    List<BookDto> getAllBooks();

    BookDto getBookById(Long bookId);

    BookDto updateBook(BookDto book);

    void deleteBook(Long bookId);
}
