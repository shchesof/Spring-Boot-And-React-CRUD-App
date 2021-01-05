package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.BookDto;
import cz.cvut.fel.sin.dto.LibraryDto;
import cz.cvut.fel.sin.service.LibraryServiceImpl;
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
@WebMvcTest(LibraryController.class)
public class LibraryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryServiceImpl libraryService;

    @Test
    public void createLibraryTest() throws Exception {
        LibraryDto libraryDto = new LibraryDto("library", "CVUT FEL");

        when(libraryService.createNewLibrary(any(LibraryDto.class))).thenReturn(libraryDto);

        mockMvc.perform(post(LibraryController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(libraryDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(libraryDto.getName()))
            .andExpect(jsonPath("$.address").value(libraryDto.getAddress()));
    }

    @Test
    public void getAllLibrariesTest() throws Exception {
        LibraryDto library1 = new LibraryDto("library1", "CVUT FEL");
        LibraryDto library2 = new LibraryDto("library2", "CVUT FEL");

        List<LibraryDto> libraries = new ArrayList<>();
        libraries.add(library1);
        libraries.add(library2);

        when(libraryService.getAllLibraries()).thenReturn(libraries);

        mockMvc.perform(get(LibraryController.BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getLibraryByIdTest() throws Exception {
        Long id = 1L;
        LibraryDto libraryDto = new LibraryDto("library", "CVUT FEL");
        libraryDto.setId(id);

        when(libraryService.getLibraryById(anyLong())).thenReturn(libraryDto);

        mockMvc.perform(get(LibraryController.DETAIL_URI_LIBRARY, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", equalTo(id.intValue())));
    }

    @Test
    public void addBookIntoLibraryTest() throws Exception {
        Long bookId = 1L;
        BookDto bookDto = new BookDto("Test book", "Horror", "1111", new Date("22/01/1999"));
        bookDto.setId(bookId);

        Long libraryId = 2L;
        LibraryDto libraryDto = new LibraryDto("CVUT", "Dejvice");
        libraryDto.setId(libraryId);

        libraryDto.getBooksIds().add(bookDto.getId());

        when(libraryService.addBookIntoLibrary(anyLong(), anyLong())).thenReturn(libraryDto);

        mockMvc.perform(put(LibraryController.DETAIL_URI_BOOK, bookId, libraryId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.booksIds[0]", equalTo(bookId.intValue())));
    }

    @Test
    public void deleteLibraryTest() throws Exception {
        mockMvc.perform(delete(LibraryController.DETAIL_URI_LIBRARY, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(libraryService, times(1)).deleteLibrary(1L);
    }
}
