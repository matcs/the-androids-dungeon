package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleById(int id) {
        return roleRepository.findByRoleId(id);
    }

    public Role saveRole(Role obj) {
        return roleRepository.save(obj);
    }

    public Role updateRole(int id, Role obj) {
        Role role = findRoleById(id);
        role.setRoleName(obj.getRoleName());

        return roleRepository.save(role);
    }

    public Role deleteRole(int id) {
        Role role = findRoleById(id);
        if (role != null) roleRepository.delete(role);
        return role;
    }
}
