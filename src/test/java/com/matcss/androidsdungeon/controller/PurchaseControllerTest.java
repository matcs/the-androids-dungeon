package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.service.PurchaseService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PurchaseController.class, PurchaseService.class})
@WebMvcTest(PurchaseController.class)
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    private static final String urlTemplate = "/purchases";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PurchaseService purchaseService;

    @Test
    public void givenAllPurchases_WhenGetPurchases_ThenReturnHttpStatus200() throws Exception {
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(1, 5, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1)));
        purchases.add(new Purchase(2, 15, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1)));
        purchases.add(new Purchase(3, 25, 654.50f, LocalDate.now().toString(), new Customer(2), new Product(3)));

        given(purchaseService.findAllPurchases()).willReturn(purchases);

        mockMvc
                .perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].purchaseId").value(purchases.get(0).getPurchaseId()));
    }

    @Test
    public void givenPurchaseById_WhenGetPurchase_ThenReturnHttpStatus200() throws Exception {
        Purchase purchase = new Purchase(1, 5, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1));

        given(purchaseService.findPurchaseById(1)).willReturn(purchase);

        mockMvc
                .perform(get(urlTemplate + "/{purchaseId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseId").value(purchase.getPurchaseId()));
    }

    @Test
    public void createPurchase_ThenReturnHttpStatus201() throws Exception {
        Purchase purchase = new Purchase(1, 5, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1));

        given(purchaseService.savePurchase(purchase,1,1)).willReturn(purchase);

        mockMvc
                .perform(post(urlTemplate)
                        .param("productId","1")
                        .param("customerId","1")
                        .content(mapper.writeValueAsString(purchase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.purchaseId").value(purchase.getPurchaseId()));
    }


}
