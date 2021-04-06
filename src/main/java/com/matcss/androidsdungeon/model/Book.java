package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "language", length = 6)
    private String language;

    @Column(name = "isbn10", unique = true, length = 40)
    private String isbn10;

    @Column(name = "dimensions", unique = true, length = 12)
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

    public Book(String language, String isbn10, String dimensions) {
        this.language = language;
        this.isbn10 = isbn10;
        this.dimensions = dimensions;
    }

    public Book(int bookId, String language, String isbn10, String dimensions) {
        this.bookId = bookId;
        this.language = language;
        this.isbn10 = isbn10;
        this.dimensions = dimensions;
    }

    public Book(int bookId, String language, String isbn10, String dimensions, Product product) {
        this.bookId = bookId;
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
        return Objects.equals(language, book.language) && Objects.equals(isbn10, book.isbn10);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, isbn10);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name=" + product.getName() +
                ", language='" + language + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", dimensions='" + dimensions + '\'' +
                '}';
    }
}
