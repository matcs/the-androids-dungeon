package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.config.SpringSecurityWebAuxTestConfig;
import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.service.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AddressController.class, AddressService.class, SpringSecurityWebAuxTestConfig.class})
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    private static final String urlTemplate = "/addresses";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    @MockBean
    private AddressService addressService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithUserDetails("user-s")
    public void givenAddresses_whenGetAddresses_thenReturnHttpStatus() throws Exception {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1, "Los Santos", "Drummond Street", "951", "12771", "None", user));
        addresses.add(new Address(2, "Los Santos", "Ryder Avenue", "4695", "98109", "Wall Mart", user));
        addresses.add(new Address(3, "Los Santos", "Austin Secret Lane", "1778", "85001", "None", user));

        given(addressService.findAllAddresses()).willReturn(addresses);

        mockMvc
                .perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].addressId").value(addresses.get(0).getAddressId()));
    }

    @Test
    @WithUserDetails("user-s")
    public void givenAddressById_whenGetAddress_ReturnId() throws Exception {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        Address address = new Address(1, "Los Santos", "Drummond Street", "951", "12771", "None", user);

        when(addressService.findAddressById(1)).thenReturn(address);

        mockMvc
                .perform(get(urlTemplate + "/{addressId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(address.getAddressId()));
    }

    @Test
    @WithMockUser(username = "null", password = "null")
    public void createAddress_thenShowHisJson() throws Exception {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        Address address = new Address(1, "Los Santos", "Drummond Street", "951", "12771", "None", user);

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
    @WithMockUser(username = "null", password = "null")
    public void updateAddressData() throws Exception {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        Address addressData = new Address(1, "Los Sapos", "Drummond Street", "951", "12771", "None", user);

        Address addressBody = new Address(1, "Los Santos", "Drummond Street", "951", "12771", "None");

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
