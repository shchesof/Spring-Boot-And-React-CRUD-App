package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.AuthorDto;
import cz.cvut.fel.sin.dto.PublishingHouseDto;
import cz.cvut.fel.sin.service.AuthorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
@WebMvcTest(AuthorController.class)
public class AuthorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @Test
    public void createAuthorTest() throws Exception {
        AuthorDto authorDto = new AuthorDto("Sofia", "Shchepetova", "email@google.com");

        when(authorService.createNewAuthor(any(AuthorDto.class))).thenReturn(authorDto);

        mockMvc.perform(post(AuthorController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(authorDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value(authorDto.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(authorDto.getLastName()))
            .andExpect(jsonPath("$.email").value(authorDto.getEmail()));
    }

    @Test
    public void getAllAuthorsTest() throws Exception {
        AuthorDto author1 = new AuthorDto("Sofia", "Shchepetova", "email@google.com");
        AuthorDto author2 = new AuthorDto("Karel", "Novak", "karel@google.com");

        List<AuthorDto> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get(AuthorController.BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAuthorByIdTest() throws Exception {
        Long id = 1L;
        AuthorDto authorDto = new AuthorDto("Sofia", "Shchepetova", "email@google.com");
        authorDto.setId(id);

        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);

        mockMvc.perform(get(AuthorController.DETAIL_URI_AUTHOR, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", equalTo(id.intValue())));
    }

    @Test
    public void makeAgreementTest() throws Exception {
        Long authorId = 1L;
        AuthorDto authorDto = new AuthorDto("Sofia", "Shchepetova", "email@google.com");
        authorDto.setId(authorId);

        Long houseId = 2L;
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("house1", "CVUT FEL");
        publishingHouseDto.setId(houseId);

        authorDto.getPublishingHousesIds().add(publishingHouseDto.getId());

        when(authorService.makeAgreementWithPublishingHouse(anyLong(), anyLong())).thenReturn(authorDto);

        mockMvc.perform(put(AuthorController.DETAIL_URI_HOUSE, authorId, houseId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.publishingHousesIds[0]", equalTo(houseId.intValue())));
    }

    @Test
    public void deleteAuthorTest() throws Exception {
        mockMvc.perform(delete(AuthorController.DETAIL_URI_AUTHOR, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
