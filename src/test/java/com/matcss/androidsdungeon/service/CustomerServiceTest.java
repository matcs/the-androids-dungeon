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

import java.time.LocalDate;


//TODO: delete test
@Slf4j
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Before
    public void setup() {
        Customer customerModel = new Customer(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");

        Mockito
                .when(customerRepository.findCustomerByCustomerId(customerModel.getCustomerId()))
                .thenReturn(customerModel);

        Mockito
                .when(customerRepository.save(Mockito.any(Customer.class)))
                .thenAnswer(i -> i.getArguments()[0]);

    }

    @Test
    public void customerFindByIdServiceFoundCostumerTest() {
        Customer customer = customerService.findCustomerById(1);
        Customer customerModel = new Customer(1, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");

        Assertions.assertEquals(customerModel, customer);
    }

    @Test
    public void customerFindByIdServiceNotFoundCostumerTest() {
        Customer customer = customerService.findCustomerById(2);

        Assertions.assertNull(customer);
    }

    @Test
    public void createCustomer() {
        Customer customerModel = new Customer(2, "dsfjiosj@gmail.com", "54654ds", "Mc", "Poze", "15/02/2021");
        Customer customerCreated = customerService.saveCustomer(customerModel);

        Assertions.assertEquals(customerModel, customerCreated);
    }

    @Test
    public void updateCustomer() {
        Customer customerBody = new Customer(1, "madfa", "ldsamlçfda", "Alamo", "Silva", "t");
        Customer customerUpdated = customerService.updateCustomer(1, customerBody);

        Assertions.assertEquals(customerBody, customerUpdated);

        Assertions.assertEquals(LocalDate.now().toString(), customerUpdated.getUpdateAt());
    }

    @TestConfiguration
    static class CustomerServiceTestConfiguration {
        @Bean("customerServiceTestBean")
        public CustomerService customerService() {
            return new CustomerService();
        }
    }

}
