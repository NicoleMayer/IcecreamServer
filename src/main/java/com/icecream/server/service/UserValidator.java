package com.icecream.server.service;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    //TODO maybe string length check is done in the front end
    public enum ValidationResult {
        DuplicatePhoneNumber,
        DuplicateUserName,
        UsernameEmpty,
        UsernameTooShort,
        UsernameTooLong,
        PasswordEmpty,
        PasswordTooShort,
        PasswordTooLong,
        Valid
    }


    public ValidationResult validate(String phoneNumber, String username, String password) {
        if (userService.findUserByPhoneNumber(phoneNumber) != null) {
            return ValidationResult.DuplicatePhoneNumber;
        }
        if (userService.findUserByUsername(username) != null) {
            return ValidationResult.DuplicateUserName;
        }
        if (checkEmptyOrWhitespace(username)) {
            return ValidationResult.UsernameEmpty;
        }
        if (username.length() < 4) {
            return ValidationResult.UsernameTooShort;
        }
        if (username.length() > 16) {
            return ValidationResult.UsernameTooLong;
        }

        if (checkEmptyOrWhitespace(password)) {
            return ValidationResult.PasswordEmpty;
        }
        if (password.length() < 4) {
            return ValidationResult.PasswordTooShort;
        }
        if (password.length() > 16) {
            return ValidationResult.PasswordTooLong;
        }

        return ValidationResult.Valid;
    }

    private boolean checkEmptyOrWhitespace(String input) {
        return (input == null || input.trim().length() == 0);
    }
}