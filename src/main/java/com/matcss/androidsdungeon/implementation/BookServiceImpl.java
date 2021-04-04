package com.matcss.androidsdungeon.implementation;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import com.matcss.androidsdungeon.service.BookService;
import com.matcss.androidsdungeon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(int id) {
        return bookRepository.findBookByBookId(id);
    }

    @Override
    public Book saveBook(int productId, Book book) {
        Product product = productService.findById(productId);
        if (product == null) return null;
        book.setProduct(product);

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int id, Book bookBody) {
        Book book = findBookById(id);

        if (book == null) return null;

        book.setDimensions(bookBody.getDimensions());
        book.setIsbn10(bookBody.getIsbn10());
        book.setLanguage(bookBody.getLanguage());
        book.setName(bookBody.getName());

        return bookRepository.save(book);
    }

    @Override
    public Book deleteBook(int id) {
        Book book = findBookById(id);

        if (book != null) bookRepository.delete(book);

        return book;
    }
}
