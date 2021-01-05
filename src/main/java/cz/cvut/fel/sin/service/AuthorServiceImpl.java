package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.AuthorDto;
import cz.cvut.fel.sin.entity.Author;
import cz.cvut.fel.sin.entity.PublishingHouse;
import cz.cvut.fel.sin.repository.AuthorRepository;
import cz.cvut.fel.sin.repository.PublishingHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public AuthorDto createNewAuthor(AuthorDto authorDto) {
        Author author = convertAuthorDtoToEntity(authorDto);
        Author authorSaved = authorRepository.save(author);
        return convertAuthorToDto(authorSaved);
    }

    @Override
    @Transactional
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertAuthorToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto getAuthorById(Long authorId) {
        return convertAuthorToDto(authorRepository.findById(authorId).orElse(null));
    }

    @Override
    @Transactional
    public AuthorDto makeAgreementWithPublishingHouse(Long authorId, Long publishingHouseId) {
        // find author
        Author author = authorRepository.findById(authorId).orElse(null);
        // find publishing house
        PublishingHouse publishingHouse = publishingHouseRepository.findById(publishingHouseId).orElse(null);
        if (author == null || publishingHouse == null) {
            return null;
        }
        // add author to publishing house
        AuthorDto authorDto = convertAuthorToDto(author);
        authorDto.getPublishingHousesIds().add(publishingHouse.getId());
        return convertAuthorToDto(authorRepository.save(convertAuthorDtoToEntity(authorDto)));
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        return createNewAuthor(authorDto);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        if (author != null) {
            author.getBooks().stream().forEach(book -> book.getAuthors().remove(author));
            author.getPublishingHouses().stream().forEach(house -> house.getAuthors().remove(author));
            authorRepository.delete(author);
        }
    }

    private AuthorDto convertAuthorToDto(Author author) {
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
        authorDto.setPublishingHousesIds(author.getPublishingHouses()
                .stream()
                .map(house -> house.getId())
                .collect(Collectors.toSet()));
        return authorDto;
    }

    private Author convertAuthorDtoToEntity(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);
        author.setPublishingHouses(authorDto.getPublishingHousesIds()
                .stream()
                .map(id -> publishingHouseRepository.findById(id).orElse(null))
                .collect(Collectors.toSet()));
        return author;
    }
}
