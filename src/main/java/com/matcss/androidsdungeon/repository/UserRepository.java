package com.matcss.androidsdungeon.repository;

import com.matcss.androidsdungeon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserId(int customerId);
    User findUserByEmail(String email);
}
