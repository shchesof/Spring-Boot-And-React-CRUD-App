package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.PublishingHouseDto;
import cz.cvut.fel.sin.service.PublishingHouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = PublishingHouseController.BASE_URI)
public class PublishingHouseController {

    public static final String BASE_URI = "/publishingHouses";
    private static final String DETAIL_SUBURI = "/{publishingHouseId}";
    public static final String DETAIL_URI_HOUSE = BASE_URI + DETAIL_SUBURI;
    public static final String DETAIL_URI_BOOK = BASE_URI + BookController.DETAIL_SUBURI + DETAIL_SUBURI;

    private final PublishingHouseServiceImpl publishingHouseService;

    @Autowired
    public PublishingHouseController(PublishingHouseServiceImpl publishingHouseService) {
        this.publishingHouseService = publishingHouseService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public PublishingHouseDto createNewPublishingHouse(@RequestBody PublishingHouseDto publishingHouseDto) {
        return publishingHouseService.createNewPublishingHouse(publishingHouseDto);
    }

    @GetMapping
    public List<PublishingHouseDto> getAllPublishingHouses() {
        return publishingHouseService.getAllPublishingHouses();
    }

    @GetMapping(DETAIL_SUBURI)
    public PublishingHouseDto getPublishingHouseById(@PathVariable Long publishingHouseId) {
        return publishingHouseService.getPublishingHouseById(publishingHouseId);
    }

    @PutMapping(BookController.DETAIL_SUBURI + DETAIL_SUBURI)
    public PublishingHouseDto releaseBook(@PathVariable Long bookId,
                                          @PathVariable Long publishingHouseId) {
        return publishingHouseService.releaseBook(bookId, publishingHouseId);
    }

    @PutMapping(DETAIL_SUBURI)
    public PublishingHouseDto updatePublishingHouse(@RequestBody PublishingHouseDto publishingHouseDto) {
        return publishingHouseService.updatePublishingHouse(publishingHouseDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(DETAIL_SUBURI)
    public void deletePublishingHouse(@PathVariable Long publishingHouseId) {
        publishingHouseService.deletePublishingHouse(publishingHouseId);
    }
}
