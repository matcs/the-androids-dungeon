package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @TestConfiguration
    static class RoleServiceTestConfiguration {
        @Bean
        public RoleService roleService(){
            return new RoleService();
        }
    }

    @Before
    public void setup() {
        Role role = new Role(1,"USER");

        Mockito
                .when(roleRepository.findByRoleId(1))
                .thenReturn(role);

        Mockito
                .when(roleRepository.save(any(Role.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void findRoleByIdAndCheckIfIsEqualsToRoleModel() {
        Role role = roleService.findById(1);
        Role roleModel = new Role(1,"USER");

        assertEquals(roleModel, role);
    }

    @Test
    public void notFindRoleByIdAndCheckIfIsEqualsToNull() {
        Role role = roleService.findById(2);

        assertNull(role);
    }

    @Test
    public void createNewRoleObject() {
        Role role = new Role(1,"USER");

        Role createdRole = roleService.create(role);

        assertEquals(role, createdRole);
    }

    @Test
    public void findRoleByIdAndUpdate() {
        Role role = new Role(1,"USER");
        Role updatedRole = roleService.update(1,role);
        Role roleCompareModel = new Role(1,"USER");

        assertEquals(updatedRole, roleCompareModel);
    }
}
