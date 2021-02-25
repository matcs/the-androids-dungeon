package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CustomerController.class, CustomerService.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest  {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void givenCustomers_whenGetCustomers_thenReturnHttpStatus() throws Exception {

        Customer customer = new Customer(1,"odisfj","dfjids","sdlfkjodf","fdiog","sdfj");

        List<Customer> allCustomers = Arrays.asList(customer);

        given(customerService.findAllCustomer()).willReturn(allCustomers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()));
    }

    @Test
    public void givenCustomerById_whenGetCustomer_ReturnId() throws Exception {

        Customer customer = new Customer(1,"odisfj","dfjids","sdlfkjodf","fdiog","sdfj");

        when(customerService.getCustomerResponseEntity(1)).thenReturn(ResponseEntity.ok(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andDo(print());
    }

    @Test
    public void createCustomer_thenShowHisJson() throws Exception {
        Customer customer = new Customer(1,"odisfj","dfjids","sdlfkjodf","fdiog","sdfj");


        when(customerService.createCustomer(customer)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andDo(print());
    }

    @Test
    public void updateCustomerData() throws Exception {
        Customer customerData = new Customer("odisfj","dfjids","sdlfkjodf","fdiog","sdfj");

        Customer customerBody = new Customer("djsfnkjsdn@gmail.com","123456","Alex","Pacino", LocalDate.now().toString());

        when(customerService.getCustomerResponseEntity(1)).thenReturn(ResponseEntity.ok(customerData));
        when(customerService.updateCustomerPersonalData(1, customerBody)).thenReturn(customerBody);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.update_at").value(LocalDate.now().toString()))
                .andDo(print());
    }

}
