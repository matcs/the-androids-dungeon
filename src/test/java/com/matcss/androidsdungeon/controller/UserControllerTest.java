package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.service.UserService;
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
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CustomerController.class, UserService.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    private final Date date = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    //TODO: add more users
    public void givenCustomers_whenGetCustomers_thenReturnHttpStatus() throws Exception {

        User user = new User(1, "odisfj", "dfjids", "sdlfkjodf", "fdiog", date);

        List<User> allUsers = Arrays.asList(user);

        given(userService.findAllCustomer()).willReturn(allUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    public void givenCustomerById_whenGetCustomer_ReturnId() throws Exception {

        User user = new User(1, "odisfj", "dfjids", "sdlfkjodf", "fdiog", date);

        when(userService.getCustomerResponseEntity(1)).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andDo(print());
    }

    @Test
    public void createCustomer_thenShowHisJson() throws Exception {
        User user = new User(1, "odisfj", "dfjids", "sdlfkjodf", "fdiog", date);


        when(userService.saveCustomer(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andDo(print());
    }

    @Test
    public void updateCustomerData() throws Exception {
        User userData = new User("odisfj", "dfjids", "sdlfkjodf", "fdiog", date);

        User userBody = new User("djsfnkjsdn@gmail.com", "123456", "Alex", "Pacino", new Date(System.currentTimeMillis()));

        when(userService.getCustomerResponseEntity(1)).thenReturn(ResponseEntity.ok(userData));
        when(userService.updateCustomer(1, userBody)).thenReturn(userBody);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.updateAt").value(LocalDate.now().toString()))
                .andDo(print());
    }

}
