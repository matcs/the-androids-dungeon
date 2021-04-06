package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.service.CreditCardService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CreditCardController.class, CreditCardService.class})
@WebMvcTest(CreditCardController.class)
@AutoConfigureMockMvc
public class CreditCardControllerTest {

    private static final String urlTemplate = "/credit_card";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @Test
    public void givenCreditCardById_whenGetCreditCard_ReturnId() throws Exception {
        final int customerId = 1;
        CreditCard creditCard = new CreditCard(1, "5334449697390149", "123", "06/28", true, new User());

        when(creditCardService.findCreditCardById(1)).thenReturn(creditCard);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate + "/{creditCardId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creditCardId").value(creditCard.getCreditCardId()));
    }

    @Test
    public void createCreditCard_thenShowHisJson() throws Exception {
        final int customerId = 1;
        CreditCard creditCard = new CreditCard(1, "5334449697390149", "123", "06/28", true);

        when(creditCardService.saveCreditCard(customerId, creditCard)).thenReturn(creditCard);

        mockMvc
                .perform(MockMvcRequestBuilders.post(urlTemplate)
                        .param("customerId", String.valueOf(customerId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(creditCard)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.creditCardId").value(creditCard.getCreditCardId()))
                .andDo(print());
    }

    @Test
    public void updateCreditCardData() throws Exception {
        CreditCard creditCardData = new CreditCard(1, "5334449697390149", "123", "06/28", true, new User());

        CreditCard creditCardBody = new CreditCard(1, "5334449697390149", "123", "06/28");

        when(creditCardService.findCreditCardById(1)).thenReturn(creditCardData);
        when(creditCardService.updateCreditCard(1, creditCardBody)).thenReturn(creditCardBody);

        mockMvc
                .perform(MockMvcRequestBuilders.put("/credit_card/{creditCardId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(creditCardBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.validated").value(false))
                .andDo(print());
    }
}
