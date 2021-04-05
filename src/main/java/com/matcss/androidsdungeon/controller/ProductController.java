package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category-name}")
    public ResponseEntity<List<Product>> getAllAvailableProductsByCategory(@PathVariable("category-name") String categoryName){
        List<Product> products = productService.findAllAvailableProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int id){
        Product product = productService.findProductById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product productBody){
        Product product = productService.saveProduct(productBody);
        return product != null ? ResponseEntity.created(URI.create("product"+product.getProductId())).body(product) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int id, @RequestBody Product productBody){
        Product product = productService.updateProduct(id, productBody);
        return ResponseEntity.accepted().body(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
