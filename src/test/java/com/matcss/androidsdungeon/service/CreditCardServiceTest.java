package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import com.matcss.androidsdungeon.repository.UserRepository;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
@RunWith(SpringRunner.class)
//TODO: payment function
public class CreditCardServiceTest {

    private final Date date = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();

    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new User());
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);
        Mockito
                .when(creditCardRepository.findCreditCardByCreditCardId(creditCardModel.getCreditCardId()))
                .thenReturn(creditCardModel);

        Mockito
                .when(creditCardRepository.save(Mockito.any(CreditCard.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito
                .when(userRepository.findUserByUserId(1)).thenReturn(user);
    }

    @Test
    public void creditCardFindByIdServiceFoundCostumerTest() {
        CreditCard creditCard = creditCardService.findCreditCardById(1);
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new User());

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
        CreditCard creditCardModel = new CreditCard(1, "5334449697390149", "123", "06/28", true, new User());
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