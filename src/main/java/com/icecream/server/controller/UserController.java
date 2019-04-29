package com.icecream.server.controller;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.RssFeedService;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;


/**
 * This class is a rest controller for user login and register.
 * @author Kemo
 * @author NicoleMayer
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
   * This method is to deal with login request.
   *
   * @param user user in the post data, which is an instance of User.
   * @return String a state whether is login or not.
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
   * This method is to deal with login request.
   *
   * @param user user in the post data, which is an instance of {@link User}.
   * @return String a state whether the user is registered or not.
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
    try {
      userService.save(user);
      return outputResponse("Valid");
    } catch (DataAccessException ex) {
      return outputResponse("Fail");
    }


  }

  /**
   * Check if the phone number already exists, before registering,
   * the check code will send only if the phone number is correct.
   *
   * @param user user in the post data, which is an instance of {@link User}.
   * @return java.lang.String The request state.
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
   * This is a until method, just for turn the output response from a json object to the string.
   *
   * @param result The result needed to be parsed to a response.
   * @return java.lang.String The response.
   */
  private String outputResponse(String result) {
    try {
      return new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      logger.warning("json sexception");
      return "json fail";
    }
  }
}
