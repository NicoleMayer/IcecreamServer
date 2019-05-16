package com.icecream.server.controller;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.entity.User;
import com.icecream.server.service.user.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class is a rest controller for user login and register.
 *
 * @author Kemo
 * @author NicoleMayer
 */
@RestController
public class UserController {

  private final transient UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * This method is to deal with login client.
   *
   * @param user user in the post data, which is an instance of User.
   * @return String a state whether is login or not.
   */
  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public LoginResponse loginUser(final @RequestBody User user) {
    return userService.loginUser(user);
  }

  /**
   * This method is to deal with login client.
   *
   * @param user user in the post data, which is an instance of {@link User}.
   * @return String a state whether the user is registered or not.
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public NormalResponse registerUser(final @RequestBody User user) {
    return userService.registerUser(user);
  }

  /**
   * Check if the phone number already exists, before registering,
   * the check code will send only if the phone number is correct.
   *
   * @param user user in the post data, which is an instance of {@link User}.
   * @return java.lang.String The client state.
   */
  @RequestMapping(value = "/before-register", method = RequestMethod.POST)
  public NormalResponse beforeRegister(final @RequestBody User user) {
    NormalResponse msg = new NormalResponse("verify if the phone number exists");
    String phoneNumber = user.getPhoneNumber();
    if (phoneNumber == null) {
      msg.setMessage("phone number is null");
      msg.setMsgCode(0);
    } else if (userService.findByPhoneNumber(phoneNumber) != null) {
      msg.setMessage("phone number already exists");
      msg.setMsgCode(1);
    } else {
      msg.setMessage("phone number doesn't exist");
      msg.setMsgCode(2);
    }
    return msg;
  }

  /**
   * Get the user's basic information. Currently it is only username.
   *
   * @param phone user phone number.
   * @return username
   */
  @RequestMapping(value = {"/userinfo"}, method = RequestMethod.GET)
  public NormalResponse userInfo(String phone) {
    NormalResponse msg = new NormalResponse("get the user basic information");
    if (phone == null) {
      msg.setMessage("phone number is null");
      msg.setMsgCode(0);
    } else {
      User user = userService.findByPhoneNumber(phone);
      if (user == null) {
        msg.setMessage("user not found");
        msg.setMsgCode(1);
      } else {
        msg.setMessage(user.getUsername());
        msg.setMsgCode(2);
      }
    }
    return msg;
  }

}
