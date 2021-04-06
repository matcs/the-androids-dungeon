package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AddressServiceTest {

    private final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    @Autowired
    private AddressService addressService;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);
        Address address = new Address(1, "", "msaidhnaf", "456", "064444", "None", user);

        Mockito
                .when(addressRepository.findAddressByAddressId(address.getAddressId()))
                .thenReturn(address);
        Mockito
                .when(addressRepository.save(Mockito.any(Address.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito
                .when(userService.findCustomerById(1))
                .thenReturn(user);
    }

    @Test
    public void saveNewCustomerAddressInDatabase() {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);
        Address address = new Address(1, "", "msaidhnaf", "456", "064444", "None", user);
        Address createdAddress = addressService.saveAddress(1, address);

        Assertions.assertEquals(address, createdAddress);
    }

    @Test
    public void findAddressById() {
        Address address = addressService.findAddressById(1);
        Address addressModel = new Address(1, "", "msaidhnaf", "456", "064444", "None");

        Assertions.assertEquals(addressModel, address);
    }

    @Test
    public void updateAddress() {
        Address addressBody = new Address(1, "", "Pato", "2500a", "4564654", "None");
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
