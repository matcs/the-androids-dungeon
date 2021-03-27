package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ProductCategoryDataLoader implements ApplicationRunner {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDataLoader(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productCategoryRepository.saveAll(generateProductCategory());
    }

    private List<ProductCategory> generateProductCategory(){
        return Arrays.asList(
                new ProductCategory(new Product(3), new Category(6)),
                new ProductCategory(new Product(4), new Category(6)),
                new ProductCategory(new Product(5), new Category(7))
        );
    }
}
