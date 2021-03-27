package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.ProductCategory;
import com.matcss.androidsdungeon.service.ProductCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductCategoryController.class, ProductCategoryService.class})
@WebMvcTest(ProductCategoryController.class)
@AutoConfigureMockMvc
public class ProductCategoryControllerTest {

    private static final String urlTemplate = "/product-categories";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCategoryService;

    @Test
    public void getProductCategoryById_ThenReturnHttpStatus200() throws Exception {
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(new ProductCategory(1,new Product(1),new Category(1)));
        productCategories.add(new ProductCategory(1,new Product(2),new Category(1)));

        given(productCategoryService.findById(1)).willReturn(productCategories.get(0));

        mockMvc
                .perform(get(urlTemplate+"/{productCategoryId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCategoryId").value(productCategories.get(0).getProductCategoryId()));

    }

    @Test
    public void createProductCategory_And_ShowHisJson() throws Exception {
        ProductCategory productCategory = new ProductCategory(1,new Product(1),new Category(1));


        given(productCategoryService.create(any(ProductCategory.class))).willReturn(productCategory);

        mockMvc
                .perform(post(urlTemplate)
                        .param("productId","1")
                        .param("categoryId","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCategoryId").value(productCategory.getProductCategoryId()));

    }

}
