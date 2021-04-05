package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.service.AddressService;
import com.matcss.androidsdungeon.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Address>> getAllAddresses(){
        List<Address> addresses = addressService.findAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    /*@GetMapping("/customer/{customerId}")
    public List<Address> getAddressesByCustomer(@PathVariable("customerId") int id){
        return addressService.findAddressById(id);
    }*/

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable("addressId") int id){
        Address address = addressService.findAddressById(id);

        return address != null ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();

    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Address> createAddress(@PathVariable("customerId") int id, @RequestBody Address addressBody){
        Address createdAddress = addressService.saveAddress(id, addressBody);
        return ResponseEntity.created(URI.create("/address/"+createdAddress.getAddressId())).body(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateCustomer(@PathVariable("addressId") int id, @RequestBody Address addressBody){
        Address address = addressService.updateAddress(id,addressBody);
        return ResponseEntity.accepted().body(address);
    }


}
