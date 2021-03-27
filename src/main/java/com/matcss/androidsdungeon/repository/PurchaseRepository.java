package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findByPurchaseId(int id);
    List<Purchase> findAllByCustomer_CustomerId(int id);
}
