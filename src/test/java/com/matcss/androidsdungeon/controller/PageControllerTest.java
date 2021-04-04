package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.service.PageService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PageController.class, PageService.class})
@WebMvcTest(PageController.class)
@AutoConfigureMockMvc
public class PageControllerTest {

    private static final String urlTemplate = "/pages";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageService pageService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void givenAllPage_WhenGetPages_thenReturnHttpStatus200() throws Exception {
        List<Page> pages = new ArrayList<>();
        pages.add(new Page(1,"null".getBytes(),1));
        pages.add(new Page(2,"null".getBytes(),2));
        pages.add(new Page(3,"null".getBytes(),3));

        given(pageService.findAll()).willReturn(pages);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].pageId").value(pages.get(0).getPageId()));
    }
    
    @Test
    public void getAllPagesByBookId() throws Exception{
        final int bookId = 1;

        Book book1 = new Book(1,"One Piece","PT","6984716861","20x21x12");
        List<Page> pagesBook1 = new ArrayList<>();
        pagesBook1.add(new Page(1,"null".getBytes(),1, book1));
        pagesBook1.add(new Page(2,"null".getBytes(),2, book1));
        pagesBook1.add(new Page(3,"null".getBytes(),3, book1));

        Book book2 = new Book(2,"Book 2","JP","8151499281","20x21x12");
        List<Page> pagesBook2 = new ArrayList<>();
        pagesBook2.add(new Page(4,"null".getBytes(),1, book2));
        pagesBook2.add(new Page(5,"null".getBytes(),2, book2));
        pagesBook2.add(new Page(6,"null".getBytes(),3, book2));


        when(pageService.findAllPagesByBookId(1)).thenReturn(pagesBook1);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate+"/book?bookId="+bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andDo(print());
    }

    @Test
    @Ignore
    public void createNewPageInABook() throws Exception {
        Book book = new Book(1,"One Piece","PT","6984716861","20x21x12");
        Page page = new Page(1,"null".getBytes(),1);

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "file.png",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        "test".getBytes());

        when(bookRepository.findBookByBookId(1)).thenReturn(book);
        when(pageService.create(page)).thenReturn(page);

        mockMvc
                .perform(MockMvcRequestBuilders.multipart(urlTemplate)
                        .file(file)
                        .param("bookId","1")
                        .param("pageNumber","1"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Ignore
    public void updateBookPageData() throws Exception {
        Book book = new Book(1,"One Piece","PT","6984716861","20x21x12");
        Page pageData =  new Page(1,"testestest".getBytes(),1, book);
        Page pageBody =  new Page(1, "null".getBytes(), book);

        when(pageService.findById(1)).thenReturn(pageData);
        when(pageService.update(1,pageBody)).thenReturn(pageBody);

        mockMvc
                .perform(MockMvcRequestBuilders.multipart(urlTemplate+"/{pageId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pageBody)))
                .andExpect(status().isAccepted())
                .andDo(print());

    }



}
