package com.icecream.server.service.user;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.entity.User;

import java.util.Optional;

/**
 * This is the Service interface for {@link User}.
 *
 * @author NicoleMayer
 */
public interface UserService {

  User findByPhoneNumber(String phoneNumber);

  Optional<User> findById(long id);

  LoginResponse loginUser(User user);

  NormalResponse registerUser(User user);

  Long verifyToken(String token);
}