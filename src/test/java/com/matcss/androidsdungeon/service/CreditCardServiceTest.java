package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import com.matcss.androidsdungeon.repository.CustomerRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
//TODO: payment function
public class CreditCardServiceTest {

    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Before
    public void setup() {
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new Customer());
        Customer customer = new Customer(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");
        Mockito
                .when(creditCardRepository.findCreditCardByCreditCardId(creditCardModel.getCreditCardId()))
                .thenReturn(creditCardModel);

        Mockito
                .when(creditCardRepository.save(Mockito.any(CreditCard.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito
                .when(customerRepository.findCustomerByCustomerId(1)).thenReturn(customer);
    }

    @Test
    public void creditCardFindByIdServiceFoundCostumerTest() {
        CreditCard creditCard = creditCardService.findCreditCardById(1);
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new Customer());

        Assertions.assertEquals(creditCardModel, creditCard);
    }

    @Test
    public void creditCardFindByIdServiceNotFoundCostumerTest() {
        CreditCard creditCard = creditCardService.findCreditCardById(2);

        Assertions.assertNull(creditCard);
    }

    @Test
    public void createCreditCard() {
        final int customerId = 1;
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new Customer());
        CreditCard creditCardCreated = creditCardService.saveCreditCard(customerId, creditCardModel);

        Assertions.assertEquals(creditCardModel, creditCardCreated);
    }

    @Test
    public void updateCreditCard() {
        CreditCard creditCardBody = new CreditCard("5334449697390149", "123", "06/28", true);
        CreditCard creditCardUpdated = creditCardService.updateCreditCard(1, creditCardBody);
        CreditCard creditCardModelCompare = new CreditCard(1, "5334449697390149", "123", "06/28", true);

        Assertions.assertEquals(creditCardModelCompare, creditCardUpdated);
    }

    @TestConfiguration
    static class CreditCardServiceTestConfiguration {
        @Bean("creditCardTestBean")
        public CreditCardService creditCardService() {
            return new CreditCardService();
        }
    }
}