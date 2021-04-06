package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.User;
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

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


//TODO: delete test
@Slf4j
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private final Date date = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        User user = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        Mockito
                .when(userRepository.findUserByUserId(user.getUserId()))
                .thenReturn(user);

        Mockito
                .when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

    }

    @Test
    public void customerFindByIdServiceFoundCostumerTest() {
        User user = userService.findCustomerById(1);
        User userModel = new User(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);

        Assertions.assertEquals(userModel, user);
    }

    @Test
    public void customerFindByIdServiceNotFoundCostumerTest() {
        User user = userService.findCustomerById(2);

        Assertions.assertNull(user);
    }

    @Test
    public void createCustomer() {
        User userModel = new User(2, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", date);
        User userCreated = userService.saveCustomer(userModel);

        Assertions.assertEquals(userModel, userCreated);
    }

    @Test
    public void updateCustomer() {
        User userBody = new User(1, "madfa", "ldsamlçfda", "Alamo", "Silva", date);
        User userUpdated = userService.updateCustomer(1, userBody);

        Assertions.assertEquals(userBody, userUpdated);

        Assertions.assertEquals(LocalDate.now().toString(), userUpdated.getUpdateAt());
    }

    @TestConfiguration
    static class CustomerServiceTestConfiguration {
        @Bean("userServiceTestBean")
        public UserService customerService() {
            return new UserService();
        }
    }

}
