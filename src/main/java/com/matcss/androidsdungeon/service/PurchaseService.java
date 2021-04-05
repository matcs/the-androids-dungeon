package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase findPurchaseById(int id) {
        return purchaseRepository.findByPurchaseId(id);
    }

    public Purchase savePurchase(Purchase obj, int customerId, int productId) {
        obj.setCustomer(new Customer(customerId));
        obj.setProduct(new Product(productId));

        return purchaseRepository.save(obj);
    }

    public Purchase delete(int id) {
        Purchase purchase = findPurchaseById(id);
        if (purchase != null) purchaseRepository.delete(purchase);

        return purchase;
    }
}
