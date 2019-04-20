package com.icecream.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class to holds to home page.
 * @author NicoleMayer
 */
@RestController
public class Hello {

  /**
   * This is the home page for icecream server.
   */
  @RequestMapping("/")
  public String hello() {
    return "Welcome to Icecream";
  }

}
