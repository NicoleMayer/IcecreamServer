package com.icecream.server.service.user;

import com.icecream.server.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * This is the Service interface for {@link User}.
 *
 * @author NicoleMayer
 */
public interface UserService {

  void saveUser(User user);

  List<User> getAllUsers();

  boolean check(User user, String password);

  User findByPhoneNumber(String phoneNumber);

  Optional<User> findById(long id);

  List<User> findAll();

  User getCurrentUser();
}