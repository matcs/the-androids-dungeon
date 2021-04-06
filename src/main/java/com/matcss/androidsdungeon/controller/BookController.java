package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Book;
import com.matcss.androidsdungeon.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Book>> getAllBook() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") int id) {
        Book book = bookService.findBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Book> createBook(@RequestBody Book bookBody, @RequestParam("productId") int productId) {
        Book book = bookService.saveBook(productId, bookBody);
        return book != null ? ResponseEntity.created(URI.create("book/" + book.getBookId())).body(book) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") int id, @RequestBody Book bookBody) {
        Book book = bookService.updateBook(id, bookBody);
        return book != null ? ResponseEntity.accepted().body(book) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") int id) {
        Book book = bookService.deleteBook(id);
        return book != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
