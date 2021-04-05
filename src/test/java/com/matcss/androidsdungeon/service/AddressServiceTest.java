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
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setup() {
        Customer customer = new Customer(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");
        Address addressModel = new Address(1, "msaidhnaf", "456", "064444", "Santos", "None", 1);

        Mockito
                .when(addressRepository.findAddressByAddressId(addressModel.getAddressId()))
                .thenReturn(addressModel);
        Mockito
                .when(addressRepository.save(Mockito.any(Address.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito
                .when(customerService.findCustomerById(1))
                .thenReturn(customer);
    }

    @Test
    public void saveNewCustomerAddressInDatabase() {
        Customer customer = new Customer(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");
        Address address = new Address(1, "msaidhnaf", "456", "064444", "Santos", "None", 1, customer);
        Address createdAddress = addressService.saveAddress(1, address);

        Assertions.assertEquals(address, createdAddress);
    }

    @Test
    public void findAddressById() {
        Address address = addressService.findAddressById(1);
        Address addressModel = new Address(1, "msaidhnaf", "456", "064444", "Santos", "None", 1);

        Assertions.assertEquals(addressModel, address);
    }

    @Test
    public void updateAddress() {
        Address addressBody = new Address(1, "Pato", "2500a", "4564654", "São Paulo", "None", 1);
        Address addressUpdated = addressService.updateAddress(1, addressBody);

        Assertions.assertEquals(addressBody, addressUpdated);
    }

    @TestConfiguration
    static class AddressServiceTestConfiguration {
        @Bean("addressServiceTestBean")
        public AddressService addressService() {
            return new AddressService();
        }

    }

}
