package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
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
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ProductService productService;

    @Before
    public void setup() {
        Product product = new Product(1, true, "One Piece", "byte".getBytes(), "", 5f);
        Book book = new Book(1, "One Piece", "PT", "545465", "20x21x12", product);

        Mockito
                .when(bookRepository.findBookByBookId(book.getBookId()))
                .thenReturn(book);
        Mockito
                .when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito
                .when(productService.findProductById(1))
                .thenReturn(product);
    }

    @Test
    public void saveNewBookInDatabase() {
        Book book = new Book(1, "One Piece", "PT", "545465", "20x21x12", new Product(1));

        Book createdBook = bookService.saveBook(1, book);

        Assertions.assertEquals(book, createdBook);
    }

    @Test
    public void findBookByIdAndReturnABookClass() {
        Book book = bookService.findBookById(1);
        Book bookModel = new Book(1, "One Piece", "PT", "545465", "20x21x12", new Product(1, true, "One Piece", "byte".getBytes(), "", 5f));

        Assertions.assertEquals(book, bookModel);
    }

    @Test
    public void notFindBookAndReturnNull() {
        Book book = bookService.findBookById(2);

        Assertions.assertNull(book);
    }

    @Test
    public void findBookByIdAndUpdateBook() {
        Book bookModel = new Book(1, "One Piece", "PT", "545465", "20x21x12", new Product(1, true, "One Piece", "byte".getBytes(), "", 5f));
        Book book = bookService.updateBook(1, bookModel);
        Assertions.assertEquals(book, bookModel);
    }

    @TestConfiguration
    static class BookServiceTestConfiguration {
        @Bean("bookServiceTestBean")
        public BookService bookService() {
            return new BookService();
        }

    }


}
