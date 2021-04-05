package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import com.matcss.androidsdungeon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public ResponseEntity<Customer> getCustomerResponseEntity(int id) {
        Customer customer = findCustomerById(id);
        return customer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(customer);
    }

    public Customer findCustomerById(int id) {
        return customerRepository.findCustomerByCustomerId(id);
    }

    public Customer saveCustomer(Customer customer) {
        Role role = roleRepository.findRoleByRoleName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        customer.setPassword(passwordEncoder().encode(customer.getPassword()));
        customer.setUpdateAt(LocalDate.now().toString());
        customer.setRoles(roles);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int id, Customer customerBody) {
        Customer customer = findCustomerById(id);

        customer.setFirstName(customerBody.getFirstName());
        customer.setLastName(customerBody.getLastName());
        customer.setEmail(customerBody.getEmail());
        customer.setPassword(customerBody.getPassword());
        customer.setUpdateAt(LocalDate.now().toString());

        return customerRepository.save(customer);
    }

    //TODO: fix the delete function
    public Customer deleteCustomerById(int id) {
        Customer customer = findCustomerById(id);
        if (customer != null) customerRepository.delete(customer);

        return customer;
    }


}
