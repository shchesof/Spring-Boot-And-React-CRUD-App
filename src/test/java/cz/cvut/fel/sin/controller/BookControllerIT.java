package cz.cvut.fel.sin.controller;

import cz.cvut.fel.sin.dto.BookDto;
import cz.cvut.fel.sin.service.BookServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void createBookTest() throws Exception {
        BookDto bookDto = new BookDto("Test book", "Horror", "1111", new Date("22/01/1999"));

        when(bookService.createNewBook(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(post(BookController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(bookDto.getName()))
            .andExpect(jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void getAllBooksTest() throws Exception {
        BookDto book1 = new BookDto("Test book 1", "Horror", "1111", new Date("22/01/1999"));
        BookDto book2 = new BookDto("Test book 2", "Comedy", "2222", new Date("02/11/2005"));

        List<BookDto> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get(BookController.BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        Long id = 1L;
        BookDto bookDto = new BookDto("Test book", "Horror", "1111", new Date("22/01/1999"));
        bookDto.setId(id);

        when(bookService.getBookById(anyLong())).thenReturn(bookDto);

        mockMvc.perform(get(BookController.DETAIL_URI, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", equalTo(id.intValue())));
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete(BookController.DETAIL_URI, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }
}
