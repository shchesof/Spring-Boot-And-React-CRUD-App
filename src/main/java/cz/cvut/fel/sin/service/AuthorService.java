package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto createNewAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long authorId);

    AuthorDto makeAgreementWithPublishingHouse(Long authorId, Long publishingHouseId);

    AuthorDto updateAuthor(AuthorDto authorDto);

    void deleteAuthor(Long authorId);
}
