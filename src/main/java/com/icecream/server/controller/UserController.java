package com.icecream.server.controller;

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

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private UserValidator userValidator;

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
    String response = "fail";
    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      e.printStackTrace();
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
    String response = result;
    try {
      userService.save(new User(phoneNumber, username, password));
    } catch (DataAccessException ex) {
      result = "Fail";
    }

    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      e.printStackTrace();
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
    System.out.println(result+"?????");
    try {
      response = new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return response;
  }
}