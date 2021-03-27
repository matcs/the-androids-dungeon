package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(1)
public class CustomerDataLoader implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        customerRepository.saveAll(generateCustomerList());
        log.info("done!");
    }

    private List<Customer> generateCustomerList(){
        log.info("generating customers...");
        return Arrays.asList(
                new Customer("jjjjs@gmai.com","123456","Bike","Uatuzoyky","15/03/2021"),
                new Customer("mjackson@gmai.com","heehee","Mike","Jackson","14/03/2021")
        );
    }
}
