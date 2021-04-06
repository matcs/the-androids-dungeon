package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAllProducts();

        return products != null ? ResponseEntity.ok(products) : ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category-name}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    public ResponseEntity<List<Product>> getAllAvailableProductsByCategory(@PathVariable("category-name") String categoryName){
        List<Product> products = productService.findAllAvailableProductsByCategory(categoryName);

        return products != null ? ResponseEntity.ok(products) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int id){
        Product product = productService.findProductById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Product> createProduct(@RequestBody Product productBody){
        Product product = productService.saveProduct(productBody);

        return product != null ? ResponseEntity.created(URI.create("product"+product.getProductId())).body(product) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int id, @RequestBody Product productBody){
        Product product = productService.updateProduct(id, productBody);

        return product != null ? ResponseEntity.accepted().body(product) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int id){
        Product product = productService.deleteProduct(id);

        return product != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
