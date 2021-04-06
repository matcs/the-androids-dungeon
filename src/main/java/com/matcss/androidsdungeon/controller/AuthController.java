package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.AuthenticationResponse;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.UserRepository;
import com.matcss.androidsdungeon.service.UserDetailsServiceImpl;
import com.matcss.androidsdungeon.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody User customer) throws Exception {
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(customer.getUsername());

        if (userDetails == null)
            throw new Exception();

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(), userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        final String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUserRoleUser(@RequestBody User customer) {

        if (emailExist(customer.getEmail()))
            throw new IllegalArgumentException("There is an account with that email address: " + customer.getEmail());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customer.getUsername(),
                        customer.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), customer.getAuthorities());

        final String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }
}
