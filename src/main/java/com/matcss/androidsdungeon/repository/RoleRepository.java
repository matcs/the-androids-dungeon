package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleId(int id);
    Role findRoleByRoleName(String name);
}
