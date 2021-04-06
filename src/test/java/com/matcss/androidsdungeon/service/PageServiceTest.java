package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Page;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired
    private PageService pageService;

    @MockBean
    private PageRepository pageRepository;

    @MockBean
    private BookRepository bookRepository;

    @Before
    public void setup() {
        Page page = new Page(1, "null".getBytes(), 1);
        Book book = new Book(1, "", "", "", new Product(1));

        Mockito
                .when(pageRepository.findPageByPageId(1))
                .thenReturn(page);
        Mockito
                .when(pageRepository.save(Mockito.any(Page.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito
                .when(bookRepository.findBookByBookId(1)).thenReturn(book);
    }

    @Test
    public void saveNewPageInDatabase() {
        Page page = new Page(1, "null".getBytes(), 1);

        Page createdPage = pageService.savePage(1, page);

        Assertions.assertEquals(page, createdPage);
    }

    @Test
    public void notFindPageThenReturnNull() {
        Page page = pageService.findPageById(2);

        Assertions.assertNull(page);
    }

    @Test
    public void findPageByIdThenReturnAPageClass() {
        Page page = pageService.findPageById(1);
        Page pageModel = new Page(1, "null".getBytes(), 1);

        Assertions.assertEquals(page, pageModel);
    }

    @Test
    public void updatePage() {
        Page pageBody = new Page("null".getBytes(), 1);
        Page page = pageService.updatePage(1, pageBody);
        Page pageModelCompare = new Page(1, "null".getBytes(), 1);

        Assertions.assertEquals(page, pageModelCompare);
    }

    @TestConfiguration
    static class PageServiceTestConfiguration {
        @Bean("pageServiceTestBean")
        public PageService pageService() {
            return new PageService();
        }
    }
}
