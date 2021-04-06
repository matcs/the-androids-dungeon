package com.matcss.androidsdungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.matcss.androidsdungeon.repository"})
@ComponentScan(basePackages = {
                "com.matcss.androidsdungeon.model",
                "com.matcss.androidsdungeon.controller",
                "com.matcss.androidsdungeon.service",
                "com.matcss.androidsdungeon.infrastructure.seeder",
                "com.matcss.androidsdungeon.config"})
@SpringBootApplication
public class AndroidsdungeonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndroidsdungeonApplication.class, args);
    }

    /*@Bean
    CommandLineRunner init(UserRepository userRepository) {

        return (args) -> {
            userRepository.save(new User("myuser", "mypassword", true));
        };
    }*/
}
