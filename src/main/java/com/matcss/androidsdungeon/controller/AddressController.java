package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses(){
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/customer/{customerId}")
    public List<Address> getAddressesByCustomer(@PathVariable("customerId") int id){
        return addressService.getAllAddressesByCustomerId(id);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable("addressId") int id){
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Address> createAddress(@PathVariable("customerId") int id, @RequestBody Address addressBody){
        addressBody.setCustomer(new Customer(id));
        Address createdAddress = addressService.createAddress(addressBody);
        return ResponseEntity.accepted().body(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateCustomer(@PathVariable("addressId") int id, @RequestBody Address addressBody){
        Address address = addressService.updateAddress(id,addressBody);
        return ResponseEntity.accepted().body(address);
    }


}
