package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    private String name;

    private String language;

    private String isbn10;

    private String dimensions;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Page> pages;

    public Book(int bookId) {
        this.bookId = bookId;
    }

    public Book(String name, String language, String isbn10, String dimensions) {
        this.name = name;
        this.language = language;
        this.isbn10 = isbn10;
        this.dimensions = dimensions;
    }

    public Book(int bookId, String name, String language, String isbn10, String dimensions) {
        this.bookId = bookId;
        this.name = name;
        this.language = language;
        this.isbn10 = isbn10;
        this.dimensions = dimensions;
    }

    public Book(int bookId, String name, String language, String isbn10, String dimensions, Product product) {
        this.bookId = bookId;
        this.name = name;
        this.language = language;
        this.isbn10 = isbn10;
        this.dimensions = dimensions;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && Objects.equals(name, book.name) && Objects.equals(language, book.language) && Objects.equals(isbn10, book.isbn10) && Objects.equals(dimensions, book.dimensions) && Objects.equals(product, book.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, language, isbn10, dimensions, product);
    }
}
