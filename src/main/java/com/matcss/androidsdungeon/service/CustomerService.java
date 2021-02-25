package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    public ResponseEntity<Customer> getCustomerResponseEntity(int id){
        Customer customer = findCustomerById(id);
        return customer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(customer);
    }

    public Customer findCustomerById(int id){
        return customerRepository.findCustomerByCustomerId(id);
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomerPersonalData(int id, Customer customerBody){
        Customer customer = findCustomerById(id);

        customer.setFirst_name(customerBody.getFirst_name());
        customer.setLast_name(customerBody.getLast_name());
        customer.setEmail(customerBody.getEmail());
        customer.setPassword(customerBody.getPassword());
        customer.setUpdate_at(LocalDate.now().toString());

        final Customer updatedCustomer = customerRepository.save(customer);

        return updatedCustomer;
    }

    //TODO: fix the delete function
    public Customer deleteCustomerById(int id){
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);

        return customer;
    }



}
