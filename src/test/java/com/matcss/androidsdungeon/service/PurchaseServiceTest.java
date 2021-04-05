package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
public class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @Before
    public void setup() {
        Purchase purchase = new Purchase(1, 5, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1));

        when(purchaseRepository.findByPurchaseId(1))
                .thenReturn(purchase);
        when(purchaseRepository.save(any(Purchase.class)))
                .thenAnswer(i -> i.getArguments()[0]);

    }

    @Test
    public void findPurchaseById() {
        Purchase purchase = purchaseService.findPurchaseById(1);
        Purchase purchaseModel = new Purchase(1, 5, 654.50f, LocalDate.now().toString(), new Customer(1), new Product(1));

        assertEquals(purchase, purchaseModel);
    }

    @Test
    public void notFindPurchaseById() {
        Purchase purchase = purchaseService.findPurchaseById(2);

        assertNull(purchase);
    }

    @Test
    public void createPurchase() {
        Purchase purchaseModel = new Purchase(1, 5, 654.50f, LocalDate.now().toString());
        Purchase purchase = purchaseService.savePurchase(purchaseModel, 1, 1);

        assertEquals(purchase, purchaseModel);

    }

    @TestConfiguration
    static class PurchaseServiceTestConfiguration {
        @Bean("purchaseServiceTestBean")
        public PurchaseService purchaseService() {
            return new PurchaseService();
        }
    }

}
