package com.icecream.server.service;

import com.icecream.server.entity.User;

/**
 * This is the Service interface for {@link User}.
 *
 * @author NicoleMayer
 */
public interface UserService {

  /**
   * This method is to save the user instance to database.
   * @param user A instance of {@link User}.
   */
  void save(User user);

  /**
   * To check whether the password is correct or not.
   * @param user A instance of {@link User}.
   * @param password The request password.
   * @return boolean Whether the password is correct for the user.
   */
  boolean check(User user, String password);

  /**
   * To get the user with input phone number.
   * @param phoneNumber The input phone number.
   * @return The instance of {@link User} with the phone number.
   */
  User findByPhoneNumber(String phoneNumber);

}