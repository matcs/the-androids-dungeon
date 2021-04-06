package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Address findAddressById(int id) {
        return addressRepository.findAddressByAddressId(id);
    }

    public Address saveAddress(int customerId, Address address) {
        User user = userService.findCustomerById(customerId);

        if (user == null)
            return null;

        address.setUser(user);

        return addressRepository.save(address);
    }

    public Address updateAddress(int id, Address addressBody) {
        Address address = findAddressById(id);

        if (address == null)
            return null;

        address.setStreet(addressBody.getStreet());
        address.setCity(addressBody.getCity());
        address.setZipcode(addressBody.getZipcode());
        address.setReferencePoint(addressBody.getReferencePoint());
        address.setNumber(addressBody.getNumber());

        return addressRepository.save(address);
    }

    public Address deleteAddress(int id) {
        Address address = findAddressById(id);

        if (address != null)
            addressRepository.delete(address);

        return address;
    }
}
