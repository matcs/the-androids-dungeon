package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findByCategoryId(id);
    }

    public Category saveCategory(Category obj) {
        return categoryRepository.save(obj);
    }

    public Category updateCategory(int id, Category obj) {
        Category category = findCategoryById(id);

        if (category == null) return null;

        category.setCategoryId(id);
        category.setCategoryName(obj.getCategoryName());

        return categoryRepository.save(category);
    }

    public Category deleteCategory(int id) {
        Category category = findCategoryById(id);
        if (category != null) categoryRepository.delete(category);
        return category;
    }
}
