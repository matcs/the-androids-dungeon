package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService implements CRUDServices<Product> {

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllAvailableProducts(){
        return productRepository
                .findAll()
                .stream()
                .filter(Product::isAvailability)
                .collect(Collectors.toList());
    }

    public List<Product> findAllAvailableProductsByCategory(String categoryName){
        List<Product> products = productRepository
                .findAll()
                .stream()
                .filter(Product::isAvailability)
                .filter(p -> p.getProductCategories()
                        .stream()
                        .anyMatch(pc -> pc.getCategory().getCategoryName().equals(categoryName)))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findByProductId(id);
    }

    @Override
    public Product create(Product obj) {
        return productRepository.save(obj);
    }

    @Override
    public Product update(int id, Product obj) {
        Product product = findById(id);

        product.setAvailability(obj.isAvailability());
        product.setName(obj.getName());
        product.setPhoto(obj.getPhoto());
        product.setProvider(obj.getProvider());
        product.setStars(obj.getStars());

        return productRepository.save(product);
    }

    @Override
    public Product delete(int id) {
        Product product = findById(id);
        if(product != null) productRepository.delete(product);
        return product;
    }
}
