package com.icecream.server.controller;

import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description This class is a rest controller for user login and register
 */
@RestController
public class UserController {
  private final transient UserService userService;

  private final transient UserValidator userValidator;

  private static final String FAIL = "{\"state\":\"Fail\"}";

  static final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

  public UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  /**
   * @param user user in the post data, which is an instance of User
   * @return String a state whether is login or not
   * @description deal with login request
   * @author Kemo / modified by NicoleMayer
   * @date 2019-04-14
   */
  @PostMapping(path = "/login")
  public String login(final @RequestBody User user) {
    final String phoneNumber = user.getPhoneNumber();
    final String password = user.getPassword();
    if (phoneNumber == null || password == null) {
      return FAIL;
    }

    UserValidator.ValidationResult result = userValidator.loginValidate(phoneNumber, password);
    return outputResponse(result.toString());

  }

  /**
   * @param user user in the post data, which is an instance of User
   * @return String a state whether the user is registered or not
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
      return FAIL;
    }
    if (userValidator.registerValidate(phoneNumber)
        != UserValidator.ValidationResult.Valid) {
      return FAIL;
    }
    String result = "Valid";
    try {
      userService.save(user);
    } catch (DataAccessException ex) {
      result = "Fail";
    }

    return outputResponse(result);
  }

  /**
   * @param user
   * @return java.lang.String
   * @description check if the phone number already exists, before registering, the check code will send only if the phone number is correct
   * @author NicoleMayer
   * @date 2019-04-20
   */
  @PostMapping(path = "/before-register")
  public String beforeRegister(final @RequestBody User user) {
    String phoneNumber = user.getPhoneNumber();
    if (phoneNumber == null) {
      return FAIL;
    }
    UserValidator.ValidationResult result = userValidator.registerValidate(phoneNumber);
    return outputResponse(result.toString());
  }

  /**
   * @description: TODO
   * @param This is a until method, just for turn the output response from a json object to the string
   * @return java.lang.String
   * @author NicoleMayer
   * @date 2019-04-20
   */
  private String outputResponse(String result) {
    String response;
    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      response = "json fail";
      logger.warning("json sexception");
    }
    return response;
  }
}
