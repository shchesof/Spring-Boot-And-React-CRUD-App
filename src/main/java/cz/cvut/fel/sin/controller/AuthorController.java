package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.AuthorDto;
import cz.cvut.fel.sin.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = AuthorController.BASE_URI)
public class AuthorController {

    public static final String BASE_URI = "/authors";
    private static final String DETAIL_SUBURI_AUTHOR = "/{authorId}";
    public static final String DETAIL_URI_AUTHOR = BASE_URI + DETAIL_SUBURI_AUTHOR;
    private static final String DETAIL_SUBURI_HOUSE = "/{publishingHouseId}";
    public static final String DETAIL_URI_HOUSE = DETAIL_URI_AUTHOR + DETAIL_SUBURI_HOUSE;

    private final AuthorServiceImpl authorService;

    @Autowired
    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AuthorDto createNewAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.createNewAuthor(authorDto);
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(DETAIL_SUBURI_AUTHOR)
    public AuthorDto getAuthorById(@PathVariable Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PutMapping(DETAIL_SUBURI_AUTHOR + DETAIL_SUBURI_HOUSE)
    public AuthorDto makeAgreement(@PathVariable Long authorId,
                                   @PathVariable Long publishingHouseId) {
        return authorService.makeAgreementWithPublishingHouse(authorId, publishingHouseId);
    }

    @PutMapping(DETAIL_SUBURI_AUTHOR)
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(authorDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(DETAIL_SUBURI_AUTHOR)
    public void deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
