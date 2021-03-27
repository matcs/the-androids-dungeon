package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductController.class, ProductService.class})
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final String urlTemplate = "/products";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void givenAllProducts_WhenGetProducts_ThenReturnHttpStatus200() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, true, "One Piece", "byte".getBytes(), "", 5f));
        products.add(new Product(1, true, "Dragon Ball Z", "byte".getBytes(), "", 4.5f));
        products.add(new Product(1, true, "Naruto", "byte".getBytes(), "", 3f));

        given(productService.findAll()).willReturn(products);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productId").value(products.get(0).getProductId()));
    }

    @Test
    public void createProduct_And_ShowHisJson() throws Exception {
        Product product = new Product(1, true, "Naruto", "byte".getBytes(), "", 3f);

        when(productService.create(product)).thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(product.getProductId()))
                .andDo(print());
    }

    @Test
    public void updateProduct_And_ReturnUpdatedProduct_With_AcceptedHttpStatus() throws Exception {
        final int productId = 1;
        Product product = new Product(1, false, "Naruto", "datteBYTEyaro".getBytes(), "Brazil", 3f);
        Product productBody = new Product(true, "One Piece", "byte".getBytes(), "Japan", 5f);
        Product updatedProduct = new Product(1,true, "One Piece", "byte".getBytes(), "Japan", 5f);

        when(productService.findById(productId)).thenReturn(product);
        when(productService.update(Mockito.anyInt(),
                Mockito.any(Product.class))).thenReturn(updatedProduct);

        mockMvc
                .perform(MockMvcRequestBuilders.put(urlTemplate + "/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productBody)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}
