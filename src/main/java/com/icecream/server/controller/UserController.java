package com.icecream.server.controller;

import java.util.logging.Logger;
import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description This class is a rest controller for user login and register
 */
@RestController
public class UserController {
  @Autowired
  private transient UserService userService;

  @Autowired
  private transient UserValidator userValidator;

  static final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

  /**
   * @param phoneNumber, password
   * @return String
   * @description deal with login request
   * @author Kemo / modified by NicoleMayer
   * @date 2019-04-14
   */
  @PostMapping(path = "/login")
  public String login(@RequestParam(name = "phone") String phoneNumber,
                      @RequestParam String password) {
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
   * @param phoneNumber, username, password
   * @return String
   * @description deal with register request
   * @author Kemo / modified by NicoleMayer
   * @date 2019-04-14
   */
  @PostMapping(path = "/register")
  public String register(@RequestParam(name = "phone") String phoneNumber,
                         @RequestParam String username,
                         @RequestParam String password) {
    String result = "Valid";
    String response;
    try {
      userService.save(new User(phoneNumber, username, password));
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
   * @description: check if the phone number already exists
   * @param phoneNumber, username, password
   * @return java.lang.String
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
