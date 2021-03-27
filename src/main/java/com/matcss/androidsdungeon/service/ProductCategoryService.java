package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService implements CRUDServices<ProductCategory> {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findById(int id) {
        return productCategoryRepository.findByProductCategoryId(id);
    }

    @Override
    public ProductCategory create(ProductCategory obj) {
        return productCategoryRepository.save(obj);
    }

    @Override
    public ProductCategory update(int id, ProductCategory obj) {
        return null;
    }

    @Override
    public ProductCategory delete(int id) {
        return null;
    }
}
