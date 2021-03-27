package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AddressDataLoader implements ApplicationRunner {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressDataLoader(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addressRepository.saveAll(generateAddressList());
        log.info("done!");
    }

    private List<Address> generateAddressList(){
        log.info("generating addresses...");
        return Arrays.asList(
                //Customer id: 1
                new Address("Paulista","123","3336620","Centro","none",1, new Customer(1)),
                new Address("Avenida Conselheiro Carlos Alberto Barros Sampaio","458","49081-010","Capucho","none",1, new Customer(1)),

                //Customer id: 2
                new Address("Praça Hélio Alves Garcia","002","12226-592","Campos de São José","none",1, new Customer(2))
        );
    }
}
