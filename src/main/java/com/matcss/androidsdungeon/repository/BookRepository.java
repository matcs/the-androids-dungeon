package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookId(int id);
}
