package com.matcss.androidsdungeon.service;

import com.matcss.androidsdungeon.model.Role;
import com.matcss.androidsdungeon.model.User;
import com.matcss.androidsdungeon.repository.UserRepository;
import com.matcss.androidsdungeon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final Set<Role> roles = new HashSet<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> findAllCustomer() {
        return userRepository.findAll();
    }

    public ResponseEntity<User> getCustomerResponseEntity(int id) {
        User user = findCustomerById(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    public User findCustomerById(int id) {
        return userRepository.findUserByUserId(id);
    }

    public User saveCustomer(User user) {
        Role role = roleRepository.findRoleByRoleName("USER");

        roles.add(role);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setUpdateAt(new Date(System.currentTimeMillis()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User updateCustomer(int id, User userDto) {
        User user = findCustomerById(id);

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder().encode(userDto.getPassword()));
        user.setUpdateAt(new Date(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    //TODO: fix the delete function
    public User deleteCustomerById(int id) {
        User user = findCustomerById(id);
        if (user != null)
            userRepository.delete(user);

        return user;
    }


}
