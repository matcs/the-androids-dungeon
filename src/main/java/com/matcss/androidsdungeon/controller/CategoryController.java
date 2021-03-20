package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public  ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") int id){
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category categoryBody){
        Category category = categoryService.create(categoryBody);
        return ResponseEntity.created(URI.create("/category")).body(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") int id, @RequestBody Category categoryBody){
        Category updatedCategory = categoryService.update(id, categoryBody);
        return ResponseEntity.accepted().body(updatedCategory);
    }



}
