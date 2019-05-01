package com.icecream.server.controller;

import com.icecream.server.entity.User;
import com.icecream.server.service.user.UserService;
import com.icecream.server.service.user.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;


/**
 * This class is a rest controller for user login and register.
 * @author Kemo
 * @author NicoleMayer
 */
@RestController
public class UserController {
  private final transient UserService userService;

  private final transient UserValidator userValidator;

  private final transient RestTemplate restTemplate = new RestTemplate();

  private static final String FAIL = "{\"state\":\"Fail\"}";

  private static final Logger LOGGER = Logger.getLogger(UserController.class);

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
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(final @RequestBody User user) {
    final String phoneNumber = user.getPhoneNumber();
    final String password = user.getPassword();
    System.out.println("???????????");
    if (phoneNumber == null || password == null) {
      return FAIL;
    }

    UserValidator.ValidationResult result = userValidator.loginValidate(phoneNumber, password);
    return outputResponse(result.toString());

  }

  @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
  public String loginPage(){
    String res = restTemplate.getForObject(
            "http://localhost:8080/login",
            String.class,
            new User("123456", null, "111111"));
    if(res == null)
      res = "res is null";
    return res;
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
      userService.saveUser(user);
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

  @RequestMapping(value = {"/list/users" }, method = RequestMethod.GET) //TODO maybe
  public String listAllUsers() throws JSONException{
    List<User> users = userService.findAll();
    JSONObject msg = new JSONObject().put("users", users);
    return msg.toString();
  }

  @RequestMapping(value = "/myusername", method = RequestMethod.GET)
  @ResponseBody
  public String currentUserName(Principal principal) {
    return principal.getName();
  }

//  @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET) //TODO maybe
//  public String accessDeniedPage() {
//    return "accessDenied";
//  }
//
//  @RequestMapping(value="/logout", method = RequestMethod.GET)
//  public String logout (HttpServletRequest request, HttpServletResponse response) { //TODO 不知道对不对
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    if (auth != null){
//      new SecurityContextLogoutHandler().logout(request, response, auth);
//    }
//    return "logout";
//  }

  /**
   * This is a until method, just for turn the output response from a json object to the string.
   *
   * @param result The result needed to be parsed to a response.
   * @return java.lang.String The response.
   */
  public String outputResponse(String result) {
    try {
      return new JSONObject().put("state", result).toString();
    } catch (JSONException e) {
      LOGGER.debug("json sexception");
      return "json fail";
    }
  }
}
