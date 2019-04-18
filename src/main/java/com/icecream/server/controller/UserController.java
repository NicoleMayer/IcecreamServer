package com.icecream.server.controller;

import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.*;
import org.springframework.web.bind.annotation.*;

/**
 * @description This class is a rest controller for user login and register
 */
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
        UserValidator.ValidationResult result = userValidator.registerValidate(phoneNumber);
        if (result == UserValidator.ValidationResult.Valid) {
            userService.save(new User(phoneNumber, username, password));
        }
        String response = "fail";
        try {
            response = new JSONObject().put("state", result).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
