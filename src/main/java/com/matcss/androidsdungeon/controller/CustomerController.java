package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> userList = userService.findAllCustomer();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity<User> createCustomer(@RequestBody User userBody) {
        User user = userService.saveCustomer(userBody);
        return ResponseEntity.accepted().body(user);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getCustomerById(@PathVariable("customerId") int customerId) {
        return userService.getCustomerResponseEntity(customerId);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<User> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody User userBody) {
        User user = userService.updateCustomer(customerId, userBody);
        return ResponseEntity.accepted().body(user);
    }

    //TODO: fix the delete mapping
    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAnyAuthority('ROLE')")
    public ResponseEntity<User> deleteCustomer(@PathVariable("customerId") int customerId) {
        User user = userService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }


}
