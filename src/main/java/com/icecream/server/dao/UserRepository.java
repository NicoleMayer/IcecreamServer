package com.icecream.server.dao;

import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByPhoneNumber(String phoneNumber);
}
