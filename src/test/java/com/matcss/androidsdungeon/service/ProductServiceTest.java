package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.repository.ProductRepository;
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
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @TestConfiguration
    static class ProductServiceTestConfiguration {
        @Bean
        public ProductService productService(){
            return new ProductService();
        }
    }

    @Before
    public void setup(){
        Product product = new Product(1,true,"One Piece","byte".getBytes(),"",5f);

        Mockito
                .when(productRepository.findByProductId(1))
                .thenReturn(product);

        Mockito
                .when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void findProductByIdAndCheckIfIsEqualsToProductModel() {
        Product product = productService.findById(1);
        Product productModel = new Product(1,true,"One Piece","byte".getBytes(),"",5f);

        Assertions.assertEquals(productModel, product);
    }

    @Test
    public void notFindProductByIdAndCheckIfIsNull() {
        Product product = productService.findById(2);

        Assertions.assertNull(product);
    }

    @Test
    public void saveNewProduct(){
        Product product = new Product(1,true,"One Piece","byte".getBytes(),"",5f);

        Product createdProduct = productService.create(product);

        Assertions.assertEquals(createdProduct, product);
    }

    @Test
    public void updateProduct(){
        Product productBody = new Product(true,"Dragon Ball Z","jp-byte".getBytes(),"Japan",4.5f);
        Product productUpdated = productService.update(1,productBody);
        Product productCompareModel = new Product(1,true,"Dragon Ball Z","jp-byte".getBytes(),"Japan",4.5f);

        Assertions.assertEquals(productUpdated,productCompareModel);
    }

}
