package com.icecream.server;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.entity.User;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * UserController Tester.
 * @author nicolemayer
 */
public class UserControllerTest {

  private final transient RestTemplate restTemplate = new RestTemplate();

  private static final String PROTOCOL = "http";
  private static final String HOST = "localhost";
  private static final String PORT = "8080";
  private static final String MAIN_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

  private static final String LOGIN_URL = MAIN_URL + "signin";
  private static final String REGISTER_URL = MAIN_URL + "signup";
  private static final String BEFORE_REGISTER_URL = MAIN_URL + "before-register";

  /**
   * Test when the login is valid.
   */
  @Test
  public void testLoginValid() {
    LoginResponse loginResponse = restTemplate.postForObject(
            LOGIN_URL,
            new User("1234", null, "123456"),
            LoginResponse.class);
    assertEquals("valid login", "login succeed", loginResponse.getMessage());
  }

  /**
   * Test when the login is invalid.
   */
  @Test
  public void testLoginInvalid() {
    LoginResponse loginResponse = restTemplate.postForObject(
            LOGIN_URL,
            new User("1234562456", null, "1235656"),
            LoginResponse.class);
    assertEquals("invalid login", "can't find phone number", loginResponse.getMessage());
    loginResponse = restTemplate.postForObject(
            LOGIN_URL,
            new User("1234", null, "1235656"),
            LoginResponse.class);
    assertEquals("invalid login", "wrong password", loginResponse.getMessage());
  }


  /**
   * Check if the phone is valid before registering.
   */
  @Test
  public void testValidPhoneBeforeRegister() {
    NormalResponse normalResponse = restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User("12345623477", null, null),
            NormalResponse.class);
    assertEquals("valid phone", "phone number doesn't exist", normalResponse.getMessage());
    normalResponse = restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User("1234", null, null),
            NormalResponse.class);
    assertEquals("invalid phone", "phone number already exists", normalResponse.getMessage());
    normalResponse = restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User(null, null, null),
            NormalResponse.class);
    assertEquals("invalid phone", "phone number is null", normalResponse.getMessage());

  }

  /**
   * Check if the register is succeed.
   */
  @Test
  public void testRegister() {
    NormalResponse normalResponse = restTemplate.postForObject(
            REGISTER_URL,
            new User("12345", "nicolemayer", "123456"),
            NormalResponse.class);
    assertEquals("valid register", "register succeed", normalResponse.getMessage());

    normalResponse = restTemplate.postForObject(
            REGISTER_URL,
            new User("12345896", "nicolemayer", "123456"),
            NormalResponse.class);
    assertEquals("valid register", "register succeed", normalResponse.getMessage());

  }
}
