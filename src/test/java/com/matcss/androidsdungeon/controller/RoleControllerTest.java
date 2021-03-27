package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RoleController.class, RoleService.class})
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
public class RoleControllerTest {

    private static final String urlTemplate = "/roles";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    public void givenAllRoles_WhenGetRoles_ThenReturnHttpStatus200() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "USER"));
        roles.add(new Role(2, "ADMIN"));

        given(roleService.findAll()).willReturn(roles);

        mockMvc
                .perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roleId").value(roles.get(0).getRoleId()));
    }

    @Test
    public void getRoleById_ThenReturnHttpStatus200() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "USER"));
        roles.add(new Role(2, "ADMIN"));

        given(roleService.findById(1)).willReturn(roles.get(0));

        mockMvc
                .perform(get(urlTemplate + "/{roleId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(roles.get(0).getRoleId()));
    }

    @Test
    public void createRole_And_ShowHisJson() throws Exception {
        Role role = new Role(1, "USER");

        given(roleService.create(role)).willReturn(role);

        mockMvc
                .perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(role)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleId").value(role.getRoleId()));
    }

    @Test
    public void updateRole_And_ReturnRole_With_AcceptedHttpStatus() throws Exception {
        Role role = new Role(1, "USER");
        when(roleService.update(anyInt(), any(Role.class)))
                .thenReturn(role);

        mockMvc
                .perform(put(urlTemplate + "/{roleId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(role)))
                .andExpect(status().isAccepted());
    }
}
