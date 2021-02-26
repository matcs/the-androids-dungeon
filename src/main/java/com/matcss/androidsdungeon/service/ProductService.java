package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.model.Products;
import com.matcss.androidsdungeon.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService implements CRUDServices<Products> {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> findAllAvailableProducts(){
        return productRepository
                .findAll()
                .stream()
                .filter(Products::isAvailability)
                .collect(Collectors.toList());
    }

    public List<Products> findAllAvailableProductsByCategory(String categoryName){
        List<Products> products = productRepository
                .findAll()
                .stream()
                .filter(p -> p.getProductCategories()
                        .stream().anyMatch(pc -> pc.getCategory().getCategory_name().equals(categoryName)))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public List<Products> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Products findById(int id) {
        return productRepository.findByProductId(id);
    }

    @Override
    public Products create(Products obj) {
        return productRepository.save(obj);
    }

    @Override
    public Products update(int id, Products obj) {
        Products product = findById(id);

        product.setAvailability(obj.isAvailability());
        product.setName(obj.getName());
        product.setPhoto(obj.getPhoto());
        product.setProvider(obj.getProvider());
        product.setStars(obj.getStars());

        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        Products product = findById(id);
        productRepository.delete(product);
    }
}
