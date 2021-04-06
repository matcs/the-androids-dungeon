package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.service.AddressService;
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
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();

        return addresses != null ? ResponseEntity.ok(addresses) : ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable("addressId") int id) {
        Address address = addressService.findAddressById(id);

        return address != null ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/{customerId}")
    public ResponseEntity<Address> createAddress(@PathVariable("customerId") int id, @RequestBody Address addressBody) {
        Address address = addressService.saveAddress(id, addressBody);

        return address != null ? ResponseEntity.created(URI.create("/address/" + address.getAddressId())).body(address) : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable("addressId") int id, @RequestBody Address addressBody) {
        Address address = addressService.updateAddress(id, addressBody);

        return address != null ? ResponseEntity.accepted().body(address) : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("addressId") int id) {
        Address address = addressService.deleteAddress(id);

        return address != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
