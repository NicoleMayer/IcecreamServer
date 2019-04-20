package com.icecream.server.service;

import com.icecream.server.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
  private transient final UserService userService;

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
      if (!checkNotEmpty(phoneNumber)) { return ValidationResult.NoSuchUser; }
      User user;
      user = userService.findByPhoneNumber(phoneNumber);
      if (user == null) { return ValidationResult.NoSuchUser; }
      if (userService.check(user, password)) { return ValidationResult.Valid; }
      else { return ValidationResult.WrongPassword; }
    }


  /**
   * @param phoneNumber
   * @return
   * @description Validate register information
   */
  public ValidationResult registerValidate(String phoneNumber) {
    if (userService.findByPhoneNumber(phoneNumber) != null) {
      return ValidationResult.DuplicatePhoneNumber;
    } else {
      return ValidationResult.Valid;
    }
  }

  private boolean checkNotEmpty(String str) {
    if(str == null){ return false; }
    for(int i = 0; i < str.length(); i++) {
      if(!Character.isWhitespace(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

}
