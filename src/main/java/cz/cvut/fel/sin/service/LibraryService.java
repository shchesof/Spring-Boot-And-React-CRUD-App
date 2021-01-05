package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.LibraryDto;

import java.util.List;

public interface LibraryService {

    LibraryDto createNewLibrary(LibraryDto libraryDto);

    List<LibraryDto> getAllLibraries();

    LibraryDto getLibraryById(Long libraryId);

    LibraryDto addBookIntoLibrary(Long bookId, Long libraryId);

    LibraryDto updateLibrary(LibraryDto libraryDto);

    void deleteLibrary(Long libraryId);
}
