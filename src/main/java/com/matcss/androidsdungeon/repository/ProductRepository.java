package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    Products findByProductId(int id);
}
