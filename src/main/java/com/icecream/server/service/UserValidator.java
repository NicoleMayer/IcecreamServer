package com.icecream.server.service;

import com.icecream.server.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
  private final transient UserService userService;

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
   * @description Validate login information, check if the password is right with given phone number
   * @author kemo
   */
  public ValidationResult loginValidate(String phoneNumber, String password) {

    if (isEmpty(phoneNumber)) {
      return ValidationResult.NoSuchUser;
    }
    User user;
    user = userService.findByPhoneNumber(phoneNumber);

    if (user == null) {
      return ValidationResult.NoSuchUser;
    }
    if (userService.check(user, password)) {
      return ValidationResult.Valid;
    } else {
      return ValidationResult.WrongPassword;
    }
  }


  /**
   * @param phoneNumber
   * @return
   * @description Validate register information, check if there doesn't exist a duplicate phone
   */
  public ValidationResult registerValidate(String phoneNumber) {
    if (userService.findByPhoneNumber(phoneNumber) != null) {
      return ValidationResult.DuplicatePhoneNumber;
    } else {
      return ValidationResult.Valid;
    }
  }
/**
 * @description: check if the string is empty, until method
 * @param
 * @return boolean
 * @author NicoleMayer
 * @date 2019-04-20
 */
  private boolean isEmpty(String str) {
    if (str == null) {
      return true;
    }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

}
