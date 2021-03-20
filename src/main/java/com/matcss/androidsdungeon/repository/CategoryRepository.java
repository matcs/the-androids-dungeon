package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int id);
}
