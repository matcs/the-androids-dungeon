package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService implements CRUDServices<Purchase> {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase findById(int id) {
        return purchaseRepository.findByPurchaseId(id);
    }

    @Override
    public Purchase create(Purchase obj) {
        return null;
    }

    public Purchase create(Purchase obj, int customerId, int productId){
        obj.setCustomer(new Customer(customerId));
        obj.setProduct(new Product(productId));

        return purchaseRepository.save(obj);
    }


    @Override
    public Purchase update(int id, Purchase obj) {
        return null;
    }

    @Override
    public Purchase delete(int id) {
        Purchase purchase = findById(id);
        if(purchase != null) purchaseRepository.delete(purchase);

        return purchase;
    }
}
