package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit_card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{creditCardId}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable("creditCardId") int id){
        CreditCard creditCard = creditCardService.findById(id);

        return creditCard.equals(null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(creditCard);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CreditCard> createCreditCard(@PathVariable("customerId") int id, @RequestBody CreditCard creditCardBody){
        creditCardBody.setCustomer(new Customer(id));
        CreditCard creditCard = creditCardService.create(creditCardBody);
        return ResponseEntity.accepted().body(creditCard);
    }

    @PutMapping("/{creditCardId}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable("creditCardId") int id, @RequestBody CreditCard creditCardBody){
        CreditCard creditCard = creditCardService.update(id, creditCardBody);
        return ResponseEntity.accepted().body(creditCard);
    }
}
