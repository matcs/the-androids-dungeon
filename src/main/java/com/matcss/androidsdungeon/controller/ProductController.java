package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Products;
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
    public ResponseEntity<List<Products>> getAllAvailableProducts(){
        List<Products> products = productService.findAllAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category-name}")
    public ResponseEntity<List<Products>> getAllAvailableProductsByCategory(@PathVariable("category-name") String categoryName){
        List<Products> products = productService.findAllAvailableProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Products> getProductById(@PathVariable("productId") int id){
        Products product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products productBody){
        Products product = productService.create(productBody);
        return ResponseEntity.accepted().body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Products> updateProduct(@PathVariable("productId") int id, @RequestBody Products productBody){
        Products product = productService.update(id, productBody);
        return ResponseEntity.accepted().body(product);
    }
}
