package com.icecream.server.service;

import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private final transient UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }


  @Override
  public boolean check(User user, String password) {
    return user != null && user.getPassword().equals(password);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return userRepository.findByPhoneNumber(phoneNumber);
  }

}
