package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setup() {
        Category category = new Category(1, "MANGA");

        Mockito
                .when(categoryRepository.findByCategoryId(category.getCategoryId()))
                .thenReturn(category);
        Mockito
                .when(categoryRepository.save(Mockito.any(Category.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void saveNewCategoryInDatabase() {
        Category category = new Category(1, "MANGA");

        Category createdCategory = categoryService.saveCategory(category);

        Assertions.assertEquals(category, createdCategory);
    }

    @Test
    public void notFindCategoryThenReturnNull() {
        Category category = categoryService.findCategoryById(2);

        Assertions.assertNull(category);
    }

    @Test
    public void findCategoryByIdThenReturnACategoryClass() {
        Category category = categoryService.findCategoryById(1);
        Category categoryModel = new Category(1, "MANGA");

        Assertions.assertEquals(category, categoryModel);
    }

    @Test
    public void updateCategory() {
        Category categoryBody = new Category("GRAPHIC_NOVEL");
        Category category = categoryService.updateCategory(1, categoryBody);
        Category categoryModelCompare = new Category(1, "GRAPHIC_NOVEL");

        Assertions.assertEquals(category, categoryModelCompare);
    }

    @TestConfiguration
    static class CategoryServiceTestConfiguration {
        @Bean("categoryServiceTestBean")
        public CategoryService categoryService() {
            return new CategoryService();
        }
    }

}
