package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit_card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{creditCardId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable("creditCardId") int id) {
        CreditCard creditCard = creditCardService.findCreditCardById(id);
        return creditCard != null ? ResponseEntity.ok(creditCard) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CreditCard> createCreditCard(@RequestParam("customerId") int customerId, @RequestBody CreditCard creditCardBody) {
        CreditCard creditCard = creditCardService.saveCreditCard(customerId, creditCardBody);
        return creditCard != null ? ResponseEntity.accepted().body(creditCard) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{creditCardId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable("creditCardId") int id, @RequestBody CreditCard creditCardBody) {
        CreditCard creditCard = creditCardService.updateCreditCard(id, creditCardBody);
        return creditCard != null ? ResponseEntity.accepted().body(creditCard) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{creditCardId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CreditCard> deleteCreditCard(@PathVariable("creditCardId") int id) {
        CreditCard creditCard = creditCardService.deleteCreditCard(id);
        return creditCard != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
