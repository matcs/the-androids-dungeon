package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService implements CRUDServices<Book> {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findBookByBookId(id);
    }

    public Book create(Book obj,int id) {
        Product product = productService.findById(id);
        if(product == null)
            return null;
        obj.setProduct(new Product(product.getProductId()));
        return bookRepository.save(obj);
    }

    @Override
    public Book create(Book obj) {
        return null;
    }

    @Override
    public Book update(int id, Book obj) {
        Book book = findById(id);

        if(book == null)
            return null;

        book.setBookId(id);
        book.setDimensions(obj.getDimensions());
        book.setIsbn10(obj.getIsbn10());
        book.setLanguage(obj.getLanguage());
        book.setName(obj.getName());

        return bookRepository.save(book);
    }

    @Override
    public Book delete(int id) {
        Book book = findById(id);
        if (book != null) bookRepository.delete(book);
        return book;
    }

}
