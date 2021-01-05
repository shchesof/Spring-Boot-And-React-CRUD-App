package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.LibraryDto;
import cz.cvut.fel.sin.entity.Book;
import cz.cvut.fel.sin.entity.Library;
import cz.cvut.fel.sin.repository.BookRepository;
import cz.cvut.fel.sin.repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public LibraryDto createNewLibrary(LibraryDto libraryDto) {
        Library library = convertToEntity(libraryDto);
        Library librarySaved = libraryRepository.save(library);
        return convertToDto(librarySaved);
    }

    @Override
    @Transactional
    public List<LibraryDto> getAllLibraries() {
        List<Library> libraries = libraryRepository.findAll();
        return libraries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LibraryDto getLibraryById(Long libraryId) {
        return convertToDto(libraryRepository.findById(libraryId).orElse(null));
    }

    @Override
    @Transactional
    public LibraryDto addBookIntoLibrary(Long bookId, Long libraryId) {
        // find book
        Book book = bookRepository.findById(bookId).orElse(null);
        // find library
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (book == null || library == null) {
            return null;
        }
        int countSameBooks = (int) library.getBooks()
                .stream()
                .filter(b -> b.getName().equals(book.getName()))
                .count();
        // there can be only 5 same books in one library
        if (countSameBooks > 5) {
            return null;
        }
        // add book into library
        LibraryDto libraryDto = convertToDto(library);
        libraryDto.getBooksIds().add(book.getId());
        return convertToDto(libraryRepository.save(convertToEntity(libraryDto)));
    }

    @Override
    @Transactional
    public LibraryDto updateLibrary(LibraryDto libraryDto) {
        return createNewLibrary(libraryDto);
    }

    @Override
    @Transactional
    public void deleteLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library != null) {
            library.getBooks().stream().forEach(book -> book.getLibraries().remove(library));
            libraryRepository.delete(library);
        }
    }

    private LibraryDto convertToDto(Library library) {
        LibraryDto libraryDto = modelMapper.map(library, LibraryDto.class);
        libraryDto.setBooksIds(library.getBooks()
                .stream()
                .map(book -> book.getId())
                .collect(Collectors.toSet()));
        return libraryDto;
    }

    private Library convertToEntity(LibraryDto libraryDto) {
        Library library = modelMapper.map(libraryDto, Library.class);
        library.setBooks(libraryDto.getBooksIds()
                .stream()
                .map(id -> bookRepository.findById(id).orElse(null))
                .collect(Collectors.toSet()));
        return library;
    }
}
