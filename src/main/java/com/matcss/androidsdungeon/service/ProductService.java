package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllAvailableProducts() {
        return productRepository
                .findAll()
                .stream()
                .filter(Product::isAvailability)
                .collect(Collectors.toList());
    }

    public List<Product> findAllAvailableProductsByCategory(String categoryName) {
        return productRepository
                .findAll()
                .stream()
                .filter(Product::isAvailability)
                .filter(p -> p.getProductCategories()
                        .stream()
                        .anyMatch(pc -> pc.getCategory().getCategoryName().equals(categoryName)))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int id) {
        return productRepository.findByProductId(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product productDto) {
        Product product = findProductById(id);

        product.setAvailability(productDto.isAvailability());
        product.setName(productDto.getName());
        product.setPhoto(productDto.getPhoto());
        product.setStars(productDto.getStars());

        return productRepository.save(product);
    }

    public Product deleteProduct(int id) {
        Product product = findProductById(id);
        if (product != null) productRepository.delete(product);

        return product;
    }
}
