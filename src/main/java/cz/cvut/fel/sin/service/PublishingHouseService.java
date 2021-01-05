package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.dto.PublishingHouseDto;

import java.util.List;

public interface PublishingHouseService {

    PublishingHouseDto createNewPublishingHouse(PublishingHouseDto publishingHouseDto);

    List<PublishingHouseDto> getAllPublishingHouses();

    PublishingHouseDto getPublishingHouseById(Long publishingHouseId);

    PublishingHouseDto releaseBook(Long bookId, Long publishingHouseId);

    PublishingHouseDto updatePublishingHouse(PublishingHouseDto publishingHouseDto);

    void deletePublishingHouse(Long publishingHouseId);
}
