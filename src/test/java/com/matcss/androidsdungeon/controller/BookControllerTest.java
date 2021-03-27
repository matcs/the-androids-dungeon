package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BookController.class, BookService.class})
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String urlTemplate = "/books";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void givenAllBooks_WhenGetBooks_ThenReturnHttpStatus200() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "One Piece", "PT", "545465", "20x21x12", new Product(1)));
        books.add(new Book(2, "Dragon Ball Z", "JP", "545465", "19x2x12", new Product(2)));
        books.add(new Book(3, "Batman Arkham", "EN", "545465", "20x21x12", new Product(3)));

        given(bookService.findAll()).willReturn(books);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].bookId").value(books.get(0).getBookId()));

    }

    @Test
    public void createBook_And_ShowHisJson() throws Exception {
        final int productId = 3;
        Book book = new Book(1, "One Piece", "PT", "545465", "20x21x12");

        when(bookService.create(productId, book)).thenReturn(book);

        mockMvc
                .perform(MockMvcRequestBuilders.post(urlTemplate + "?productId=" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andDo(print());
    }

    @Test
    public void updateBook_And_ReturnUpdatedBook_With_AcceptedHttpStatus() throws Exception {
        final int bookId = 1;
        Book book = new Book(1, "OnePiece", "JP", "545465", "20x21x12");
        Book bookBody = new Book("One Piece", "PT", "545465", "20x21x12");

        when(bookService.findById(bookId)).thenReturn(book);
        when(bookService.update(bookId, bookBody)).thenReturn(bookBody);

        mockMvc
                .perform(MockMvcRequestBuilders.put(urlTemplate + "/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookBody)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    public void deleteBookById_And_CheckIfTheBookWasDeleted() throws Exception {
        final int bookId = 1;
        Book book = new Book(1, "OnePiece", "JP", "545465", "20x21x12");

        when(bookService.delete(bookId)).thenReturn(book);
        when(bookService.findById(bookId)).thenReturn(null);

        mockMvc
                .perform(MockMvcRequestBuilders.delete(urlTemplate + "/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate + "/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
