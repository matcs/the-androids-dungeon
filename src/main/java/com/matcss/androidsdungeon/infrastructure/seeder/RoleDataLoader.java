package com.matcss.androidsdungeon.infrastructure.seeder;

import com.matcss.androidsdungeon.model.Customer;
import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class RoleDataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleRepository.saveAll(generateRoleList());
    }

    private List<Role> generateRoleList(){
        return Arrays.asList(
                new Role("USER", new Customer(1)),
                new Role("USER", new Customer(2))
        );
    }
}
