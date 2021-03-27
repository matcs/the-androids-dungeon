package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
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
    public List<ProductCategory> getAllProductCategories(){
        return productCategoryService.findAll();
    }

    @GetMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable("productCategoryId") int id){
        ProductCategory productCategory = productCategoryService.findById(id);
        return productCategory != null ? ResponseEntity.ok(productCategory) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategories(@RequestParam("productId") int productId,
                                                                   @RequestParam("categoryId") int categoryId) {

        ProductCategory productCategoryBody = new ProductCategory();

        productCategoryBody.setCategory(new Category(categoryId));
        productCategoryBody.setProduct(new Product(productId));

        ProductCategory createdProductCategory = productCategoryService.create(productCategoryBody);

        return ResponseEntity.created(URI.create("/product-category/"+productId+"/"+categoryId)).body(createdProductCategory);
    }
}
