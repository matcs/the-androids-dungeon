package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService implements CRUDServices<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findByCategoryId(id);
    }

    @Override
    public Category create(Category obj) {
        return categoryRepository.save(obj);
    }

    @Override
    public Category update(int id, Category obj) {
        Category category = findById(id);

        if(category == null) return null;

        category.setCategoryId(id);
        category.setCategoryName(obj.getCategoryName());

        return categoryRepository.save(category);
    }

    @Override
    public Category delete(int id) {
        Category category = findById(id);
        if(category != null) categoryRepository.delete(category);
        return category;
    }
}
