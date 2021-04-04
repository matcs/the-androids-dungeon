package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAllAddresses();

    Address findAddressById(int id);

    Address saveAddress(int customerId, Address address);

    Address updateAddress(int id, Address address);

    Address deleteAddress(int id);
}
