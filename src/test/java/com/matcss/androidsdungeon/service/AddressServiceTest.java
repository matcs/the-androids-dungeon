package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @TestConfiguration
    static class AddressServiceTestConfiguration {
        @Bean
        public AddressService addressService(){
            return new AddressService();
        }

    }

    @Before
    public void setup(){
        Address addressModel = new Address(1,"msaidhnaf","456","064444","Santos","None",1);
        Mockito
                .when(addressRepository.findAddressByAddressId(addressModel.getAddressId()))
                .thenReturn(addressModel);

        Mockito
                .when(addressRepository.save(Mockito.any(Address.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void saveNewCustomerAddressInDatabase(){
        Customer customerModel = new Customer(1,"dsfjiosj@gmail.com","54654ds","Mc","Poze","15/02/2021");
        Address addressModel = new Address(1,"msaidhnaf","456","064444","Santos","None",1,customerModel);
        Address createdAddress = addressService.createAddress(addressModel);

        Assertions.assertEquals(addressModel, createdAddress);
    }

    @Test
    public void findAddressById(){
        Address address = addressService.getAddressById(1);
        Address addressModel = new Address(1,"msaidhnaf","456","064444","Santos","None",1);

        Assertions.assertEquals(addressModel, address);
    }

    @Test
    public void updateAddress(){
        Address addressBody = new Address(1,"Pato","2500a","4564654","São Paulo","None",1);
        Address addressUpdated = addressService.updateAddress(1,addressBody);

        Assertions.assertEquals(addressBody, addressUpdated);
    }

}
