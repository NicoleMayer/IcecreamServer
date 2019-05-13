package com.icecream.server.service.user;

import com.icecream.server.JwtTokenProvider;
import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is the implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    System.out.println(String.format("Getting user by phone number=%s", phoneNumber));
    return userRepository.findByPhoneNumber(phoneNumber);
  }

  @Override
  public Optional<User> findById(long id) {
    System.out.println(String.format("Getting user=%d", id));
    return userRepository.findById(id);
  }


  public LoginResponse loginUser(User user) {
    String phoneNumber = user.getPhoneNumber();
    String password = user.getPassword();

    User real_user = userRepository.findByPhoneNumber(phoneNumber);
    if (real_user == null) {
      return new LoginResponse("can't find phone number", 0, "");
    }

    LoginResponse loginResponse = new LoginResponse();
    String encodedPassword = real_user.getPassword();

    if (passwordEncoder.matches(password, encodedPassword)) {
      String token = jwtTokenProvider.generateToken(real_user);
      loginResponse.setMessage("login succeed");
      loginResponse.setMsgCode(2);
      loginResponse.setToken(token);
    }
    else {
      loginResponse.setMessage("wrong password");
      loginResponse.setMsgCode(1);
      loginResponse.setToken("");
    }

    return loginResponse;
  }

  @Override
  public NormalResponse registerUser(User user){
    // client already deal with the length of username password, before registering, the phone number has been checked
    // so here I don't handle these problems
    NormalResponse normalResponse = new NormalResponse("register status");

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    normalResponse.setMessage("register succeed");
    normalResponse.setMsgCode(0);
    return normalResponse;
  }


  @Override
  public Long verifyToken(String token) {
    Long id = null;
    boolean validToken = jwtTokenProvider.validateToken(token);
    if (validToken == true) {
      id = jwtTokenProvider.getUserIdFromJWT(token);
    }

    return id;
  }
}
