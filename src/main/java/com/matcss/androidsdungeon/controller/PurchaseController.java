package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.findAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("purchaseId") int id) {
        Purchase purchase = purchaseService.findPurchaseById(id);

        return purchase != null ? ResponseEntity.ok(purchase) : ResponseEntity.notFound().build();
    }

    /*@GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Purchase>> getAllPurchaseByCustomerId(@PathVariable("customerId") int id) {
        List<Purchase> purchases = purchaseService.(id);

        return purchases.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(purchases);
    }*/

    @PostMapping
    public ResponseEntity<Purchase> createNewPurchase(@RequestBody Purchase purchaseBody,
                                                      @RequestParam("productId") int productId,
                                                      @RequestParam("customerId") int customerId) {

        Purchase purchaseCreated = purchaseService.savePurchase(purchaseBody, customerId, productId);

        return ResponseEntity.created(URI.create("purchases/" + purchaseCreated.getPurchaseId())).body(purchaseCreated);
    }

}
