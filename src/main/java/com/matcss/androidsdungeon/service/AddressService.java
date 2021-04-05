package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Address findAddressById(int id) {
        return addressRepository.findAddressByAddressId(id);
    }

    public Address saveAddress(int customerId, Address address) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) return null;

        address.setCustomer(customer);

        return addressRepository.save(address);
    }

    public Address updateAddress(int id, Address addressBody) {
        Address address = findAddressById(id);

        address.setSelected_address(addressBody.getSelected_address());
        address.setCep(addressBody.getCep());
        address.setNeighborhood(addressBody.getNeighborhood());
        address.setNumber(addressBody.getNumber());
        address.setReference(addressBody.getReference());
        address.setStreet(addressBody.getStreet());

        return addressRepository.save(address);
    }

    public Address deleteAddress(int id) {
        Address address = findAddressById(id);
        addressRepository.delete(address);

        return address;
    }
}
