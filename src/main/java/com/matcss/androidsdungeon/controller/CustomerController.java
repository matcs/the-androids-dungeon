package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customerList = customerService.findAllCustomer();
        return ResponseEntity.ok(customerList);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customerBody){
        Customer customer = customerService.createCustomer(customerBody);
        return ResponseEntity.accepted().body(customer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId){
        ResponseEntity<Customer> customerResponseEntity = customerService.getCustomerResponseEntity(customerId);
        return customerResponseEntity;
    }
}
