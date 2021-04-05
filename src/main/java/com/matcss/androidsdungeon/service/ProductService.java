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

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int id) {
        return productRepository.findByProductId(id);
    }

    public Product saveProduct(Product obj) {
        return productRepository.save(obj);
    }

    public Product updateProduct(int id, Product obj) {
        Product product = findProductById(id);

        product.setAvailability(obj.isAvailability());
        product.setName(obj.getName());
        product.setPhoto(obj.getPhoto());
        product.setProvider(obj.getProvider());
        product.setStars(obj.getStars());

        return productRepository.save(product);
    }

    public Product deleteProduct(int id) {
        Product product = findProductById(id);
        if (product != null) productRepository.delete(product);

        return product;
    }
}
