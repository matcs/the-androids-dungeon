package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.AuthenticationResponse;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import com.matcss.androidsdungeon.security.CustomerDetailsService;
import com.matcss.androidsdungeon.security.JwtUtil;
import com.matcss.androidsdungeon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerDetailsService customerDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    private final JwtUtil jwtUtil;

    public AuthController(CustomerDetailsService customerDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody Customer customer) throws Exception {
        final UserDetails userDetails = customerDetailsService.loadUserByUsername(customer.getUsername());

        if (userDetails == null)
            throw new Exception();

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody Customer customer) throws Exception {

        if (emailExist(customer.getEmail())) {
            throw new Exception("There is an account with that email address: " + customer.getEmail());}

        Customer userDetails = customerService.createCustomer(customer);

        if (userDetails == null)
            throw new Exception();

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    private boolean emailExist(String email) {
        return customerRepository.findCustomerByEmail(email) != null;
    }
}
