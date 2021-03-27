package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductId(int id);
}
