package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByProductCategoryId(int id);
}
