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

    @TestConfiguration
    static class BookServiceTestConfiguration{
        @Bean
        public BookService bookService(){
            return new BookService();
        }
    }

    @Before
    public void setup(){
        Book bookModel = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));

        Mockito
                .when(bookRepository.findBookByBookId(bookModel.getBookId()))
                .thenReturn(bookModel);
        Mockito
                .when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void saveNewBookInDatabase() {
        Book book = new Book(1,"One Piece","PT","545465","20x21x12", new Product(1));

        Book createdBook = bookService.create(book);

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
