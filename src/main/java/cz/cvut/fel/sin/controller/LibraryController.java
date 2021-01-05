package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.LibraryDto;
import cz.cvut.fel.sin.service.LibraryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = LibraryController.BASE_URI)
public class LibraryController {

    public static final String BASE_URI = "/libraries";
    private static final String DETAIL_SUBURI_LIBRARY = "/{libraryId}";
    public static final String DETAIL_URI_LIBRARY = BASE_URI + DETAIL_SUBURI_LIBRARY;
    public static final String DETAIL_URI_BOOK = BASE_URI + BookController.DETAIL_SUBURI + DETAIL_SUBURI_LIBRARY;

    private final LibraryServiceImpl libraryService;

    @Autowired
    public LibraryController(LibraryServiceImpl libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public LibraryDto createNewLibrary(@RequestBody LibraryDto libraryDto) {
        return libraryService.createNewLibrary(libraryDto);
    }

    @GetMapping
    public List<LibraryDto> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @GetMapping(DETAIL_SUBURI_LIBRARY)
    public LibraryDto getLibraryById(@PathVariable Long libraryId) {
        return libraryService.getLibraryById(libraryId);
    }

    @PutMapping(DETAIL_SUBURI_LIBRARY)
    public LibraryDto updateLibrary(@RequestBody LibraryDto libraryDto) {
        return libraryService.updateLibrary(libraryDto);
    }

    @PutMapping(BookController.DETAIL_SUBURI + DETAIL_SUBURI_LIBRARY)
    @ResponseBody
    public LibraryDto addBookIntoLibrary(@PathVariable Long bookId,
                                         @PathVariable Long libraryId) {
        return libraryService.addBookIntoLibrary(bookId, libraryId);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(DETAIL_SUBURI_LIBRARY)
    public void deleteLibrary(@PathVariable Long libraryId) {
        libraryService.deleteLibrary(libraryId);
    }
}
