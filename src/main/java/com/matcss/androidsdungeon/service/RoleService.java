package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.interfaces.CRUDServices;
import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements CRUDServices<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findByRoleId(id);
    }

    @Override
    public Role create(Role obj) {
        return roleRepository.save(obj);
    }

    @Override
    public Role update(int id, Role obj) {
        Role role = findById(id);
        role.setRoleName(obj.getRoleName());

        return roleRepository.save(role);
    }

    @Override
    public Role delete(int id) {
        Role role = findById(id);
        if(role != null)roleRepository.delete(role);
        return role;
    }
}
