package com.matcss.androidsdungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.matcss.androidsdungeon.repository"})
@ComponentScan(basePackages = {"com.matcss.androidsdungeon.controller"})
@SpringBootApplication
public class AndroidsdungeonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndroidsdungeonApplication.class, args);
    }

}
