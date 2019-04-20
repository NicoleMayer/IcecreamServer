package com.icecream.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: This is the home page for icecream server
 * @param
 * @return
 * @author NicoleMayer
 * @date 2019-04-20
 */
@RestController
public class Hello {
  @RequestMapping("/")
  public String hello() {
    return "Welcome to Icecream";
  }

}
