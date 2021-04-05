package com.matcss.androidsdungeon.controller;

import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAllRoles();
        return roles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable("roleId") int id){
        Role role = roleService.findRoleById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role roleBody){
        Role role = roleService.saveRole(roleBody);
        return role != null ? ResponseEntity.created(URI.create("role/"+role.getRoleId())).body(role) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") int id, @RequestBody Role roleBody){
        Role role = roleService.updateRole(id, roleBody);
        return role != null ? ResponseEntity.accepted().body(role) : ResponseEntity.badRequest().build();
    }


}
