package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByAddressId(int id);
    List<Address> findAddressesByUser_UserId(int id);
}
