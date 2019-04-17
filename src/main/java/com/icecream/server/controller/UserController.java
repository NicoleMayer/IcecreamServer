package com.icecream.server.controller;

import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    /**
     * @description: deal with login request
     * @param phoneNumber, username, password
     * @return String
     * @author Kemo / modified by NicoleMayer
     * @date 2019-04-14
     */
    @GetMapping("/login")
    public String login(@RequestParam(name="phone",required=false) String phoneNumber,
                        @RequestParam(required=false) String username,
                        @RequestParam String password) {
        User user;



        if (username!=null && !username.isEmpty()) {
            user = userService.findUserByUsername(username);
        }
        else if  (phoneNumber!=null && !phoneNumber.isEmpty()){
            user = userService.findUserByPhoneNumber(phoneNumber);
        }
        else {
            return "fail";
        }

        if (userService.check(user, password))
            return "login";
        else
            return "fail";
    }

    /**
     * @description: deal with register request
     * @param phoneNumber, username, password
     * @return String
     * @author Kemo / modified by NicoleMayer
     * @date 2019-04-14
     */
    @PostMapping(path = "/register")
    public @ResponseBody
    String register(@RequestParam(name="phone") String phoneNumber,
                    @RequestParam String username,
                    @RequestParam String password) {
        switch (userValidator.validate(phoneNumber, username, password)) {
            case Valid:
                User user = new User(phoneNumber, username, password);
                System.out.println(phoneNumber+" "+ username+" "+password);
                userService.save(user);
                return "User saved";
            case DuplicatePhoneNumber:
                return "Phone Number already registered";
            case DuplicateUserName:
                return "Username has already been used";
            case UsernameEmpty:
                return "Empty username";
            case UsernameTooShort:
                return "username too short";
            case UsernameTooLong:
                return "username too long";
            case PasswordEmpty:
                return "Empty password";
            case PasswordTooShort:
                return "password too short";
            case PasswordTooLong:
                return "password too long";
            default:
                return "";
        }

    }
}