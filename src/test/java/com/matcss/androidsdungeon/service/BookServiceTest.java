package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @TestConfiguration
    static class BookServiceTestConfiguration {
        @Bean
        public BookService bookService(){
            return new BookService();
        }

    }

    @Before
    public void setup(){
        Book book = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));
        Product product = new Product(1,true,"One Piece","byte".getBytes(),"",5f);

        Mockito
                .when(bookRepository.findBookByBookId(book.getBookId()))
                .thenReturn(book);
        Mockito
                .when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito
                .when(productRepository.findByProductId(1))
                .thenReturn(product);
    }

    @Test
    public void saveNewBookInDatabase() {
        Book book = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));

        Book createdBook = bookService.create(1,book);

        Assertions.assertEquals(book, createdBook);
    }

    @Test
    public void findBookByIdAndReturnABookClass(){
        Book book = bookService.findById(1);
        Book bookModel = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));

        Assertions.assertEquals(book,bookModel);
    }

    @Test
    public void notFindBookAndReturnNull(){
        Book book = bookService.findById(2);

        Assertions.assertNull(book);
    }

    @Test
    public void findBookByIdAndUpdateBook(){
        Book bookModel = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));
        Book book = bookService.update(1, bookModel);
        Assertions.assertEquals(book,bookModel);
    }


}
