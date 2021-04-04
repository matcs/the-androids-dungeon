package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.implementation.AddressServiceImpl;
import com.matcss.androidsdungeon.model.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AddressController.class, AddressServiceImpl.class})
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    private static final String urlTemplate = "/addresses";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl addressService;

    @Test
    @WithMockUser(username = "matcss", password = "147258")
    public void givenAddresses_whenGetAddresses_thenReturnHttpStatus() throws Exception {

        List<Address> addresses = new ArrayList<>();

        addresses.add(new Address(1, "Drummond Street", "951", "12771", "Santos", "None", 1));
        addresses.add(new Address(2, "Ryder Avenue", "4695", "98109", "None", "Wall Mart", 1));
        addresses.add(new Address(3, "Austin Secret Lane", "1778", "85001", "None", "None", 1));

        given(addressService.findAllAddresses()).willReturn(addresses);

        mockMvc
                .perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].addressId").value(addresses.get(0).getAddressId()));
    }

    @Test
    @WithMockUser(username = "null", password = "null")
    public void givenAddressById_whenGetAddress_ReturnId() throws Exception {

        Address address = new Address(1, "Drummond Street", "951", "12771", "Santos", "None", 1);

        when(addressService.findAddressById(1)).thenReturn(address);

        mockMvc
                .perform(get(urlTemplate + "/{addressId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(address.getAddressId()));
    }

    @Test
    public void createAddress_thenShowHisJson() throws Exception {
        Address address = new Address(1, "Drummond Street", "951", "12771", "Santos", "None", 1);

        when(addressService.saveAddress(1, address)).thenReturn(address);

        mockMvc
                .perform(post(urlTemplate + "/{customerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressId").value(address.getAddressId()))
                .andDo(print());
    }

    @Test
    public void updateAddressData() throws Exception {
        Address addressData = new Address(1, "Drummond Street", "951", "12771", "Santos", "None", 1);

        Address addressBody = new Address(1, "Drummond Street", "951", "12771", "Santos", "None", 1);

        when(addressService.findAddressById(1)).thenReturn(addressData);
        when(addressService.updateAddress(1, addressBody)).thenReturn(addressBody);

        mockMvc
                .perform(put(urlTemplate + "/{addressId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addressBody)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}
