package com.matcss.androidsdungeon.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicActiveUser = new User("user-s", "pass", Arrays.asList(
                new SimpleGrantedAuthority("USER")
        ));

        User managerActiveUser = new User("managerUser", "pass1234", Arrays.asList(
                new SimpleGrantedAuthority("ADMIN")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                basicActiveUser, managerActiveUser
        ));
    }
}