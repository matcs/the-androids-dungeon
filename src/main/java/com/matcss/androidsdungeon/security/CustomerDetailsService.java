package com.matcss.androidsdungeon.security;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("in CustomerDetailsService");
        Customer customer = customerRepository.findCustomerByEmail(email);

        if (customer == null)
            throw new UsernameNotFoundException("Customer not found");

        User user = new User(customer.getUsername(), customer.getPassword(), customer.getAuthorities());

        log.info(user.toString());

        return new User(customer.getUsername(), customer.getPassword(), customer.getAuthorities());
    }
}
