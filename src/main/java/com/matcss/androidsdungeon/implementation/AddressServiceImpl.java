package com.matcss.androidsdungeon.implementation;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.AddressRepository;
import com.matcss.androidsdungeon.service.AddressService;
import com.matcss.androidsdungeon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address findAddressById(int id) {
        return addressRepository.findAddressByAddressId(id);
    }

    @Override
    public Address saveAddress(int customerId, Address address) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) return null;

        address.setCustomer(customer);

        return addressRepository.save(address);
    }

    @Override
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

    @Override
    public Address deleteAddress(int id) {
        Address address = findAddressById(id);
        addressRepository.delete(address);

        return address;
    }
}
