package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Address getAddressById(int id){
        return addressRepository.findAddressByAddressId(id);
    }

    public List<Address> getAllAddressesByCustomerId(int id){
        return addressRepository.findAddressesByCustomer_CustomerId(id);
    }

    public Address createAddress(Address addressBody){
        return addressRepository.save(addressBody);
    }

    public Address updateAddress(int id, Address addressBody){
        Address address = getAddressById(id);

        address.setSelected_address(addressBody.getSelected_address());
        address.setCep(addressBody.getCep());
        address.setNeighborhood(addressBody.getNeighborhood());
        address.setNumber(addressBody.getNumber());
        address.setReference(addressBody.getReference());
        address.setStreet(addressBody.getStreet());

        final Address updatedAddress = addressRepository.save(address);

        return updatedAddress;
    }
}
