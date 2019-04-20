package com.icecream.server.controller;

import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @description This class is a rest controller for user login and register
 */
@RestController
public class UserController {
  private final UserService userService;

  private final UserValidator userValidator;

  private final String failState = "{\"state\":\"Fail\"}";
  static final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

  public UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  /**
   * @param user user in the post data
   * @return String
   * @description deal with login request
   * @author Kemo / modified by NicoleMayer
   * @date 2019-04-14
   */
  @PostMapping(path = "/login")
  public String login(final @RequestBody User user) {
    final String phoneNumber = user.getPhoneNumber();
    final String password = user.getPassword();
    if (phoneNumber == null || password == null) {
      return failState;
    }
    UserValidator.ValidationResult result = userValidator.loginValidate(phoneNumber, password);
    String response;
    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      response = "json fail";
      logger.warning("json sexception");
    }
    return response;
  }

  /**
   * @param user user in the post data
   * @return String
   * @description deal with register request
   * @author Kemo / modified by NicoleMayer
   * @date 2019-04-14
   */
  @PostMapping(path = "/register")
  public String register(final @RequestBody User user) {
    final String phoneNumber = user.getPhoneNumber();
    final String password = user.getPassword();
    final String username = user.getUsername();
    if (phoneNumber == null || password == null || username == null) {
      return failState;
    }
    if (userValidator.registerValidate(phoneNumber)
            != UserValidator.ValidationResult.Valid) {
      return failState;
    }
    String result = "Valid";
    String response;
    try {
      userService.save(user);
    } catch (DataAccessException ex) {
      result = "Fail";
    }

    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      response = "json fail";
      logger.warning("json sexception");
    }
    return response;
  }

  /**
   * @param phoneNumber, username, password
   * @return java.lang.String
   * @description: check if the phone number already exists
   * @author NicoleMayer
   * @date 2019-04-20
   */
  @PostMapping(path = "/beforeregister")
  public String beforeRegister(@RequestParam(name = "phone") String phoneNumber) {
    UserValidator.ValidationResult result = userValidator.registerValidate(phoneNumber);
    String response = "fail";
    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      logger.warning("json sexception");
    }
    return response;
  }
}
