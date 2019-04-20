package com.icecream.server.service;

import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private final transient UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository){ this.userRepository = userRepository;}


  /**
   * @param user
   * @return void
   * @description: Save the user to user repository. Before saving, encode the password.
   * @author Kemo
   * @date 2019-04-14
   */
  @Override
  public void save(User user) {
    userRepository.save(user);
  }


  /**
   * @param user, password
   * @return boolean
   * @description: check whether the password is right
   * @author NicoleMayer
   * @date 2019-04-16
   */
  @Override
  public boolean check(User user, String password) {
    return user != null && user.getPassword().equals(password);
  }

  /**
   * @param phoneNumber
   * @return
   * @description find user by phone number
   */
  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return userRepository.findByPhoneNumber(phoneNumber);
  }

}
