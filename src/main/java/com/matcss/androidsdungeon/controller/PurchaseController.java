package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.findAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{purchaseId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    public ResponseEntity<Purchase> createNewPurchase(@RequestBody Purchase purchaseDto,
                                                      @RequestParam("productId") int productId,
                                                      @RequestParam("customerId") int customerId) {

        Purchase purchase = purchaseService.savePurchase(purchaseDto, customerId, productId);

        return purchase != null ? ResponseEntity.created(URI.create("purchases/" + purchase.getPurchaseId())).body(purchase) : ResponseEntity.badRequest().build();
    }

}
