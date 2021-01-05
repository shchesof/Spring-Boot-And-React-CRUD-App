package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.BookDto;
import cz.cvut.fel.sin.entity.Author;
import cz.cvut.fel.sin.entity.Book;
import cz.cvut.fel.sin.entity.PublishingHouse;
import cz.cvut.fel.sin.repository.AuthorRepository;
import cz.cvut.fel.sin.repository.BookRepository;
import cz.cvut.fel.sin.repository.PublishingHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public BookDto createNewBook(BookDto bookDto) {
        Book book = convertBookDtoToEntity(bookDto);
        // check if there is not book with the same ISBN
        for (Book b : bookRepository.findAll()) {
            if (b.getISBN().equals(book.getISBN())) {
                return null;
            }
        }
        // check if book's authors have agreement with book's publishing house
        for (Author author : book.getAuthors()) {
            if (!author.getPublishingHouses().contains(book.getPublishingHouse())) {
                return null;
            }
        }
        Book bookSaved = bookRepository.save(book);
        return convertBookToDto(bookSaved);
    }

    @Override
    @Transactional
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertBookToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto getBookById(Long bookId) {
        return convertBookToDto(bookRepository.findById(bookId).orElse(null));
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        return createNewBook(bookDto);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.getLibraries().stream().forEach(library -> library.getBooks().remove(book));
            book.getAuthors().stream().forEach(author -> author.getBooks().remove(book));
            if (book.getPublishingHouse() != null) {
                PublishingHouse publishingHouse =
                        publishingHouseRepository.findById(book.getPublishingHouse().getId()).orElse(null);
                publishingHouse.getBooks().remove(book);
            }
            bookRepository.delete(book);
        }
    }

    private BookDto convertBookToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setAuthorsIds(book.getAuthors().stream()
                .map(author -> author.getId())
                .collect(Collectors.toSet()));
        if (book.getPublishingHouse() != null) {
            bookDto.setPublishingHouseId(book.getPublishingHouse().getId());
        }
        return bookDto;
    }

    private Book convertBookDtoToEntity(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        book.setAuthors(bookDto.getAuthorsIds()
                .stream()
                .map(id -> authorRepository.findById(id).orElse(null))
                .collect(Collectors.toSet()));
        if (bookDto.getPublishingHouseId() != null) {
            book.setPublishingHouse(publishingHouseRepository.findById(bookDto.getPublishingHouseId()).orElse(null));
        }
        return book;
    }
}
