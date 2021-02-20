package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAllCustomer(){
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    public ResponseEntity<Customer> getCustomerResponseEntity(int id){
        Customer customer = findCustomerById(id);
        return customer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(customer);
    }

    public Customer findCustomerById(int id){
        Customer customer = customerRepository.findCustomerByCustomerId(id);
        return customer;
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

}
