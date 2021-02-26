package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements CRUDServices<Product> {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllAvailableProducts(){
        return productRepository
                .findAll()
                .stream()
                .filter(i -> i.isAvailability())
                .collect(Collectors.toList());
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
    public void delete(int id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
