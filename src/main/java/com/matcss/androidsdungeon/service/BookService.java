package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProductService productService;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(int id) {
        return bookRepository.findBookByBookId(id);
    }

    public Book saveBook(int productId, Book book) {
        Product product = productService.findProductById(productId);
        if (product == null) return null;
        book.setProduct(product);

        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book bookBody) {
        Book book = findBookById(id);

        if (book == null) return null;

        book.setDimensions(bookBody.getDimensions());
        book.setIsbn10(bookBody.getIsbn10());
        book.setLanguage(bookBody.getLanguage());

        return bookRepository.save(book);
    }

    public Book deleteBook(int id) {
        Book book = findBookById(id);

        if (book != null) bookRepository.delete(book);

        return book;
    }
}
