package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerId(int customerId);
    Customer findCustomerByEmail(String email);
}
