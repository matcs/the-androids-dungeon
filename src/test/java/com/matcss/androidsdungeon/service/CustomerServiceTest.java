package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Customer;
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
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @TestConfiguration
    static class CustomerServiceTestConfiguration {
        @Bean
        public CustomerService customerService(){
            return new CustomerService();
        }
    }

    @Before
    public void setup(){
        Customer customerModel = new Customer(1,"dsfjiosj@gmail.com","54654ds","Mc","Poze","15/02/2021");

        Mockito
                .when(customerRepository.findCustomerByCustomerId(customerModel.getCustomerId()))
                .thenReturn(customerModel);

        Mockito
                .when(customerRepository.save(Mockito.any(Customer.class)))
                .thenAnswer(i -> i.getArguments()[0]);

    }

    @Test
    public void customerFindByIdServiceFoundCostumerTest(){
        Customer customer = customerService.findCustomerById(1);
        Customer customerModel = new Customer(1,"dsfjiosj@gmail.com","54654ds","Mc","Poze","15/02/2021");

        Assertions.assertTrue(customer.equals(customerModel));
    }

    @Test
    public void customerFindByIdServiceNotFoundCostumerTest(){
        Customer customer = customerService.findCustomerById(2);

        Assertions.assertNull(customer);
    }

    @Test
    public void createCustomer(){
        Customer customerModel = new Customer(2,"dsfjiosj@gmail.com","54654ds","Mc","Poze","15/02/2021");
        Customer customerCreated = customerService.createCustomer(customerModel);

        log.error(customerCreated.toString());

        Assertions.assertTrue(customerCreated instanceof Customer);

        Assertions.assertTrue(customerCreated.equals(customerModel));
    }



}
