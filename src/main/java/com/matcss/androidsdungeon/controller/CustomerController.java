package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> GetAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> CustomerById(@PathVariable("customerId") int customerId){
        Customer customer = customerRepository.findCustomerByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }
}
