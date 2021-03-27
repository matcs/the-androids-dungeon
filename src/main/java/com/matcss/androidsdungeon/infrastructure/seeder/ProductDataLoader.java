package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
@Slf4j
public class ProductDataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productRepository.saveAll(generateProductList());
    }

    private List<Product> generateProductList(){
        return Arrays.asList(
            //3
            new Product(true,"One Piece",null,"Japan",5),
            //4
            new Product(false,"Naruto",null,"Japan",4.5f),
            //5
            new Product(true,"Batman",null,"EUA",5)
        );
    }
}
