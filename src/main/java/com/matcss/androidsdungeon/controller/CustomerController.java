package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.findAllCustomer();
        return ResponseEntity.ok(customerList);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customerBody) {
        Customer customer = customerService.saveCustomer(customerBody);
        return ResponseEntity.accepted().body(customer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId) {
        return customerService.getCustomerResponseEntity(customerId);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer customerBody) {
        Customer customer = customerService.updateCustomer(customerId, customerBody);
        return ResponseEntity.accepted().body(customer);
    }

    //TODO: fix the delete mapping
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") int customerId) {
        Customer customer = customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }


}
