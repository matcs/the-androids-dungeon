package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@Order(1)
public class CustomerDataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    @Autowired
    public CustomerDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.saveAll(generateCustomerList());
        log.info("done!");
    }

    private List<User> generateCustomerList() {
        log.info("generating customers...");
        return Arrays.asList(
                new User("jjjjs@gmai.com", (new BCryptPasswordEncoder().encode("foo")), "Bike", "Uatuzoyky", date),
                new User("mjackson@gmai.com", "heehee", "Mike", "Jackson", date)
        );
    }
}
