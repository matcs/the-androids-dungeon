package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return categories != null ? ResponseEntity.ok(categories) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") int id) {
        Category category = categoryService.findCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Category> createCategory(@RequestBody Category categoryBody) {
        Category category = categoryService.saveCategory(categoryBody);
        return ResponseEntity.created(URI.create("/category")).body(category);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") int id, @RequestBody Category categoryBody) {
        Category category = categoryService.updateCategory(id, categoryBody);
        return category != null ? ResponseEntity.accepted().body(category) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('MANAGER')")
    public ResponseEntity<Category> deleteCategory(@PathVariable("categoryId") int id) {
        Category category = categoryService.deleteCategory(id);
        return category != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
