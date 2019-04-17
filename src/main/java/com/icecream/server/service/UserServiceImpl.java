package com.icecream.server.service;

import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * @description: Save the user to user repository. Before saving, encode the password.
     * @param user
     * @return void
     * @author Kemo
     * @date 2019-04-14
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * @description: check whether the password is right
     * @param user, password
     * @return boolean
     * @author NicoleMayer
     * @date 2019-04-16
     */
    @Override
    public boolean check(User user, String password) {
        if (user == null || !user.getPassword().equals(password))
            return false;
        else
            return true;
    }

    /**
     * @description: Find the user by username
     * @param username
     * @return The result user
     * @author Kemo
     * @date 2019-04-14
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }



}
