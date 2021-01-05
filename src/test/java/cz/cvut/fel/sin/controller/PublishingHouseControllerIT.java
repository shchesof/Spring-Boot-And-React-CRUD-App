package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.BookDto;
import cz.cvut.fel.sin.dto.PublishingHouseDto;
import cz.cvut.fel.sin.service.PublishingHouseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cz.cvut.fel.sin.controller.AbstractControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PublishingHouseController.class)
public class PublishingHouseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublishingHouseServiceImpl publishingHouseService;

    @Test
    public void createPublishingHouseTest() throws Exception {
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("house1", "CVUT FEL");

        when(publishingHouseService.createNewPublishingHouse(any(PublishingHouseDto.class)))
                .thenReturn(publishingHouseDto);

        mockMvc.perform(post(PublishingHouseController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(publishingHouseDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(publishingHouseDto.getName()))
            .andExpect(jsonPath("$.address").value(publishingHouseDto.getAddress()));
    }

    @Test
    public void getAllPublishingHousesTest() throws Exception {
        PublishingHouseDto publishingHouse1 = new PublishingHouseDto("house1", "CVUT FEL");
        PublishingHouseDto publishingHouse2 = new PublishingHouseDto("house2", "CVUT FEL");

        List<PublishingHouseDto> publishingHouses = new ArrayList<>();
        publishingHouses.add(publishingHouse1);
        publishingHouses.add(publishingHouse2);

        when(publishingHouseService.getAllPublishingHouses()).thenReturn(publishingHouses);

        mockMvc.perform(get(PublishingHouseController.BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getPublishingHouseByIdTest() throws Exception {
        Long id = 1L;
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("house1", "CVUT FEL");
        publishingHouseDto.setId(id);

        when(publishingHouseService.getPublishingHouseById(anyLong())).thenReturn(publishingHouseDto);

        mockMvc.perform(get(PublishingHouseController.DETAIL_URI_HOUSE, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", equalTo(id.intValue())));
    }

    @Test
    public void releaseBookTest() throws Exception {
        Long houseId = 1L;
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("house1", "CVUT FEL");
        publishingHouseDto.setId(houseId);

        Long bookId = 2L;
        BookDto bookDto = new BookDto("Test book", "Horror", "1111", new Date("22/01/1999"));
        bookDto.setId(bookId);

        publishingHouseDto.getBooksIds().add(bookDto.getId());

        when(publishingHouseService.releaseBook(anyLong(), anyLong())).thenReturn(publishingHouseDto);

        mockMvc.perform(put(PublishingHouseController.DETAIL_URI_BOOK, bookId, houseId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.booksIds[0]", equalTo(bookId.intValue())));
    }

    @Test
    public void deletePublishingHouseTest() throws Exception {
        mockMvc.perform(delete(PublishingHouseController.DETAIL_URI_HOUSE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(publishingHouseService, times(1)).deletePublishingHouse(1L);
    }
}
