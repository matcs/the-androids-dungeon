package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
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
//TODO: payment function
public class CreditCardServiceTest {

    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @TestConfiguration
    static class CreditCardServiceTestConfiguration {
        @Bean
        public CreditCardService creditCardService(){
            return new CreditCardService();
        }
    }

    @Before
    public void setup(){
        CreditCard creditCardModel = new CreditCard(1,"5334449697390149","123","06/28",true, new Customer());

        Mockito
                .when(creditCardRepository.findCreditCardByCredit_card_id(creditCardModel.getCredit_card_id()))
                .thenReturn(creditCardModel);

        Mockito
                .when(creditCardRepository.save(Mockito.any(CreditCard.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void creditCardFindByIdServiceFoundCostumerTest(){
        CreditCard creditCard = creditCardService.findById(1);
        CreditCard creditCardModel = new CreditCard(1,"5334449697390149","123","06/28",true, new Customer());

        Assertions.assertEquals(creditCardModel, creditCard);
    }

    @Test
    public void creditCardFindByIdServiceNotFoundCostumerTest(){
        CreditCard creditCard = creditCardService.findById(2);

        Assertions.assertNull(creditCard);
    }

    @Test
    public void createCreditCard(){
        CreditCard creditCardModel = new CreditCard(1,"5334449697390149","123","06/28",true, new Customer());
        CreditCard creditCardCreated = creditCardService.create(creditCardModel);

        Assertions.assertEquals(creditCardModel, creditCardCreated);
    }

    @Test
    public void updateCreditCard(){
        CreditCard creditCardBody = new CreditCard(1,"5334449697390149","123","06/28",true, new Customer());
        CreditCard creditCardUpdated = creditCardService.update(1, creditCardBody);

        Assertions.assertEquals(creditCardBody, creditCardUpdated);
    }
}