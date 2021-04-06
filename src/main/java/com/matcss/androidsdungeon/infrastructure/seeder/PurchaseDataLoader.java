package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.model.Product;
import com.matcss.androidsdungeon.model.Purchase;
import com.matcss.androidsdungeon.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class PurchaseDataLoader implements ApplicationRunner {

    private final PurchaseRepository purchaseRepository;

    private final Date date = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();

    public PurchaseDataLoader(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        purchaseRepository.saveAll(generatePurchaseList());
        log.info("done!");
    }

    private List<Purchase> generatePurchaseList(){
        log.info("generating purchases...");
        return Arrays.asList(
                new Purchase(5,10.5f,date,new User(1),new Product(3))
        );
    }
}
