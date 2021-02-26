package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllAvailableProducts(){
        List<Product> products = productService.findAllAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product productBody){
        Product product = productService.create(productBody);
        return ResponseEntity.accepted().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int id, @RequestBody Product productBody){
        Product product = productService.update(id, productBody);
        return ResponseEntity.accepted().body(product);
    }
}
