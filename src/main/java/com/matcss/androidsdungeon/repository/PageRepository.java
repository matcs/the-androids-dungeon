package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findPageByPageId(int id);
    List<Page> findAllPageByBook_BookId(int id);
}
