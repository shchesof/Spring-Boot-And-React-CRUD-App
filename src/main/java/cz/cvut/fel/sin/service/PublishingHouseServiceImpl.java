package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.PublishingHouseDto;
import cz.cvut.fel.sin.entity.Book;
import cz.cvut.fel.sin.entity.PublishingHouse;
import cz.cvut.fel.sin.repository.BookRepository;
import cz.cvut.fel.sin.repository.PublishingHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishingHouseServiceImpl implements PublishingHouseService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PublishingHouseDto createNewPublishingHouse(PublishingHouseDto publishingHouseDto) {
        PublishingHouse publishingHouse = convertPublishingHouseDtoToEntity(publishingHouseDto);
        PublishingHouse publishingHouseSaved = publishingHouseRepository.save(publishingHouse);
        return convertPublishingHouseToDto(publishingHouseSaved);
    }

    @Override
    @Transactional
    public List<PublishingHouseDto> getAllPublishingHouses() {
        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        return publishingHouses.stream()
                .map(this::convertPublishingHouseToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PublishingHouseDto getPublishingHouseById(Long publishingHouseId) {
        return convertPublishingHouseToDto(publishingHouseRepository.findById(publishingHouseId).orElse(null));
    }

    @Override
    @Transactional
    public PublishingHouseDto releaseBook(Long bookId, Long publishingHouseId) {
        // find book
        Book book = bookRepository.findById(bookId).orElse(null);
        // find publishing house
        PublishingHouse publishingHouse = publishingHouseRepository.findById(publishingHouseId).orElse(null);
        if (book == null || publishingHouse == null || book.getPublishingHouse() != null) {
            return null;
        }
        // add book to publishing house
        PublishingHouseDto publishingHouseDto = convertPublishingHouseToDto(publishingHouse);
        publishingHouseDto.getBooksIds().add(book.getId());
        book.setPublishingHouse(publishingHouse);
        return updatePublishingHouse(publishingHouseDto);
    }

    @Override
    @Transactional
    public PublishingHouseDto updatePublishingHouse(PublishingHouseDto publishingHouseDto) {
        return createNewPublishingHouse(publishingHouseDto);
    }

    @Override
    @Transactional
    public void deletePublishingHouse(Long publishingHouseId) {
        PublishingHouse publishingHouse = publishingHouseRepository.findById(publishingHouseId).orElse(null);
        if (publishingHouse != null) {
            publishingHouse.getBooks().stream().forEach(book -> book.setPublishingHouse(null));
            publishingHouse.getAuthors().stream()
                    .forEach(author -> author.getPublishingHouses().remove(publishingHouse));
            publishingHouseRepository.delete(publishingHouse);
        }
    }

    private PublishingHouseDto convertPublishingHouseToDto(PublishingHouse publishingHouse) {
        if (publishingHouse != null) {
            PublishingHouseDto publishingHouseDto = modelMapper.map(publishingHouse, PublishingHouseDto.class);
            publishingHouseDto.setBooksIds(publishingHouse.getBooks()
                    .stream()
                    .map(book -> book.getId())
                    .collect(Collectors.toSet()));
            return publishingHouseDto;
        } else {
            return null;
        }
    }

    private PublishingHouse convertPublishingHouseDtoToEntity(PublishingHouseDto publishingHouseDto) {
        PublishingHouse publishingHouse = modelMapper.map(publishingHouseDto, PublishingHouse.class);
        publishingHouse.setBooks(publishingHouseDto.getBooksIds()
                .stream()
                .map(id -> bookRepository.findById(id).orElse(null))
                .collect(Collectors.toSet()));
        return publishingHouse;
    }
}
