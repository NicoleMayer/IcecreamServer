package com.icecream.server.service;

import com.icecream.server.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
  private final UserService userService;

  public UserValidator(UserService userService) {
    this.userService = userService;
  }

  public enum ValidationResult {
    DuplicatePhoneNumber,
    NoSuchUser,
    WrongPassword,
    Valid
  }

  /**
   * @param phoneNumber
   * @param password
   * @return
   * @description Validate login information
   * @author kemo
   */
  public ValidationResult loginValidate(String phoneNumber, String password) {
    User user = null;
    if (checkNotEmpty(phoneNumber)) {
      user = userService.findByPhoneNumber(phoneNumber);
    }
    if (user == null) {
      return ValidationResult.NoSuchUser;
    }
    System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getPhoneNumber());
    if (userService.check(user, password)) {
      return ValidationResult.Valid;
    } else {
      return ValidationResult.WrongPassword;
    }
  }

  /**
   * @param phoneNumber
   * @return
   * @description Validate register information
   */
  public ValidationResult registerValidate(String phoneNumber) {
    if (userService.findByPhoneNumber(phoneNumber) != null) {
      System.out.println("find same phone");
      return ValidationResult.DuplicatePhoneNumber;
    } else {
      return ValidationResult.Valid;
    }
  }


  private boolean checkNotEmpty(String input) {
    return (input != null && input.trim().length() != 0);
  }
}