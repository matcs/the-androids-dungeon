package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.repository.UserRepository;
import com.matcss.androidsdungeon.repository.ProductRepository;
import com.matcss.androidsdungeon.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase findPurchaseById(int id) {
        return purchaseRepository.findByPurchaseId(id);
    }

    public Purchase savePurchase(Purchase purchase, int customerId, int productId) {
        User user = userRepository.findUserByUserId(customerId);
        Product product = productRepository.findByProductId(productId);

        if (user == null || product == null)
            return null;

        purchase.setUser(user);
        purchase.setProduct(product);

        return purchaseRepository.save(purchase);
    }

    public Purchase deletePurchase(int id) {
        Purchase purchase = findPurchaseById(id);
        if (purchase != null) purchaseRepository.delete(purchase);

        return purchase;
    }
}
