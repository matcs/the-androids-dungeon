package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AddressController.class, AddressService.class})
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void givenAddresses_whenGetAddresses_thenReturnHttpStatus() throws Exception {

        Address address1 = new Address(1,"msaidhnaf","456","064444","Santos","None",1);
        Address address2 = new Address(2,"sanots","456s","dsffsd","fdh","Mercadinho",1);
        Address address3 = new Address(3,"fdg","gf","064jh444","jh","hgj",1);
        Address combinedAddressesObjs[] = { address1, address2, address3 };
        List<Address> addresses = Arrays.asList(combinedAddressesObjs);

        given(addressService.getAllAddresses()).willReturn(addresses);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].addressId").value(address1.getAddressId()));
    }

    @Test
    public void givenAddressById_whenGetAddress_ReturnId() throws Exception {

        Address address = new Address(1,"msaidhnaf","456","064444","Santos","None",1);

        when(addressService.getAddressById(1)).thenReturn(address);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/address/{addressId}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(address.getAddressId()));
    }

    @Test
    public void createAddress_thenShowHisJson() throws Exception {
        Address address = new Address(1,"msaidhnaf","456","064444","Santos","None",1);

        when(addressService.createAddress(address)).thenReturn(address);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/address/{customerId}/",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(address)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.addressId").value(address.getAddressId()))
                .andDo(print());
    }

    @Test
    public void updateAddressData() throws Exception {
        Address addressData = new Address(1,"msaidhnaf","456","064444","Santos","None",1);

        Address addressBody = new Address(1,"Pato","2500a","4564654","São Paulo","None",1);

        when(addressService.getAddressById(1)).thenReturn(addressData);
        when(addressService.updateAddress(1, addressBody)).thenReturn(addressBody);

        mockMvc
                .perform(MockMvcRequestBuilders.put("/address/{addressId}/",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressBody)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}
