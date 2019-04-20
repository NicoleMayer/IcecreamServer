package com.icecream.server.service;

import com.icecream.server.entity.User;
import org.springframework.stereotype.Component;

/**
 * This class is to validate the request state by phone number and password.
 * @author Kemo
 * @author NicoleMayer
 */
@Component
public class UserValidator {
  private final transient UserService userService;

  /**
   * The constructor.
   * @param userService A instance implemented {@link UserService}
   */
  public UserValidator(UserService userService) {
    this.userService = userService;
  }


  /**
   * The Validation states.
   */
  public enum ValidationResult {
    DuplicatePhoneNumber,
    NoSuchUser,
    WrongPassword,
    Valid
  }

  /**
   * Validate login information, check if the password is right with given phone number.
   *
   * @param phoneNumber The input phone number.
   * @param password The input password.
   * @return The validation states{@link ValidationResult}.
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
   * Validate register information, check if there doesn't exist a duplicate phone.
   *
   * @param phoneNumber The input phone number.
   * @return The validation states{@link ValidationResult}.
   */
  public ValidationResult registerValidate(String phoneNumber) {
    if (userService.findByPhoneNumber(phoneNumber) != null) {
      return ValidationResult.DuplicatePhoneNumber;
    } else {
      return ValidationResult.Valid;
    }
  }
/**
 * Check if the string is empty, until method.
 *
 * @param str Input String.
 * @return boolean Whether the input String is empty.
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
