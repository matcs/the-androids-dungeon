package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();

    Book findBookById(int id);

    Book saveBook(int productId, Book book);

    Book updateBook(int id, Book book);

    Book deleteBook(int id);
}
