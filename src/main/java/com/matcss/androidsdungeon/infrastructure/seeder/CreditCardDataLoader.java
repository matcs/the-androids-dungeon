package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.CreditCard;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CreditCardDataLoader implements ApplicationRunner {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardDataLoader(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //creditCardRepository.saveAll(generateCreditCardList());
        log.info("done!");
    }

    private List<CreditCard> generateCreditCardList(){
        log.info("generating credit cards...");
        return Arrays.asList(
                new CreditCard("5477884614340222","218","16/10/2022",true, new User(1)),
                new CreditCard("5480976880175475","957","16/10/2021",false, new User(1)),
                new CreditCard("5169228949657087","413","16/10/2022",false, new User(2))
        );
    }
}
