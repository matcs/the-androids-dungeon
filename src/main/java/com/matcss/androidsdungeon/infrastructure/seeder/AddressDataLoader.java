package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Address;
import com.matcss.androidsdungeon.model.User;
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
                //User id: 1
                new Address("Atlanta","Hanifan Lane","4617","30308","none", new User(1)),
                new Address("Atlanta","Tator Patch Road","1425","30301","none", new User(1)),

                //User id: 2
                new Address("Los Angeles","Brannon Street","2452","none","1", new User(2))
        );
    }
}
