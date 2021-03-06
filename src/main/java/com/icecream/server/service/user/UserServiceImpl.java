package com.icecream.server.service.user;

import com.icecream.server.JwtTokenProvider;
import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private transient UserRepository userRepository;

  @Autowired
  private transient PasswordEncoder passwordEncoder;

  @Autowired
  private transient JwtTokenProvider jwtTokenProvider;

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  /**
   * Find user by its phone number.
   *
   * @param phoneNumber user's phone number
   * @return user that has the phone number
   */
  @Override
  public User findByPhoneNumber(String phoneNumber) {
    logger.info(String.format("Getting user by phone number=%s", phoneNumber));
    return userRepository.findByPhoneNumber(phoneNumber);
  }

  /**
   * Find user by its id.
   *
   * @param id user's id
   * @return user that has the id
   */
  @Override
  public Optional<User> findById(long id) {
    logger.info(String.format("Getting user=%d", id));
    return userRepository.findById(id);
  }


  /**
   * Login in if the user is valid.
   *
   * @param user includes phone number and password
   * @return login response
   */
  @Override
  public LoginResponse loginUser(User user) {
    String phoneNumber = user.getPhoneNumber();

    User realUser = userRepository.findByPhoneNumber(phoneNumber);
    if (realUser == null) {
      return new LoginResponse("can't find phone number", 0, "");
    }

    LoginResponse loginResponse = new LoginResponse();
    String encodedPassword = realUser.getPassword();

    String password = user.getPassword();
    if (passwordEncoder.matches(password, encodedPassword)) {
      String token = jwtTokenProvider.generateToken(realUser);
      loginResponse.setMessage("login succeed");
      loginResponse.setMsgCode(2);
      loginResponse.setToken(token);
    } else {
      loginResponse.setMessage("wrong password");
      loginResponse.setMsgCode(1);
      loginResponse.setToken("");
    }

    return loginResponse;
  }

  /**
   * Register if the user is valid.
   *
   * @param user includes phone number and password
   * @return normal response
   */
  @Override
  public NormalResponse registerUser(User user) {
    // client already deal with the length of username password,
    NormalResponse normalResponse = new NormalResponse("register status");
    // avoid duplicate save
    if (findByPhoneNumber(user.getPhoneNumber()) != null) {
      normalResponse.setMessage("already registered");
      normalResponse.setMsgCode(1);
    } else {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      normalResponse.setMessage("register succeed");
      normalResponse.setMsgCode(0);
    }
    return normalResponse;
  }


  /**
   * Verify if the token is valid or not.
   *
   * @param token an identification for the user
   * @return the user's id if valid
   */
  @Override
  public Long verifyToken(String token) {
    Long id = null;
    boolean validToken = jwtTokenProvider.validateToken(token);
    if (validToken) {
      id = jwtTokenProvider.getUserIdFromJwt(token);
    }

    return id;
  }

}
