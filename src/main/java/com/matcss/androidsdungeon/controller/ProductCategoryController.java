package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.findAllProductCategories();
        return productCategories != null ? ResponseEntity.ok(productCategories) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{productCategoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable("productCategoryId") int id) {
        ProductCategory productCategory = productCategoryService.findProductCategoryById(id);
        return productCategory != null ? ResponseEntity.ok(productCategory) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductCategory> createProductCategories(@RequestParam("productId") int productId,
                                                                   @RequestParam("categoryId") int categoryId) {

        ProductCategory productCategory = new ProductCategory();

        productCategory.setCategory(new Category(categoryId));
        productCategory.setProduct(new Product(productId));

        ProductCategory createdProductCategory = productCategoryService.saveProductCategory(productCategory);

        return createdProductCategory != null ? ResponseEntity.created(URI.create("/product-category/" + productId + "/" + categoryId)).body(createdProductCategory) : ResponseEntity.badRequest().build();
    }
}
