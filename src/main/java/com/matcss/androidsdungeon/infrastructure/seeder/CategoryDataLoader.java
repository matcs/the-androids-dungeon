package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(3)
@Slf4j
public class CategoryDataLoader implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        categoryRepository.saveAll(generateCategoryList());
        log.info("done");
    }

    private List<Category> generateCategoryList(){
        log.info("generating categories...");
        return Arrays.asList(
                //6
                new Category("MANGA"),
                //7
                new Category("GRAPHIC_NOVEL")
        );
    }
}
