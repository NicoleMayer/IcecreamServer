package com.icecream.server;

import com.icecream.server.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * UserController Tester.
 *
 * @author nicolemayer
 * @version 1.0
 * @since <pre>Apr 17, 2019</pre>
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

  private static String VALID_STATE = "{\"state\":\"Valid\"}";
  private static String DUPLICATE_STATE = "{\"state\":\"DuplicatePhoneNumber\"}";
  private static String NOUSER_STATE = "{\"state\":\"NoSuchUser\"}";
  private static String FAIL_STATE = "{\"state\":\"Fail\"}";
  private static String PHONE = "12345623456";

  @Before
  public void before() {
  }

  @After
  public void after() {
  }

  /**
   * Test when the login is valid.
   */
  @Test
  public void testLoginValid() {
    assertEquals("valid login", VALID_STATE,
        restTemplate.postForObject(
            LOGIN_URL,
            new User("1234", null, "123456"),
            String.class)
    );
  }

  /**
   * Test when the login is invalid.
   */
  @Test
  public void testLoginInvalid() {
    assertEquals("invalid login", NOUSER_STATE,
        restTemplate.postForObject(
            LOGIN_URL,
            new User("1234562456", null, "1235656"),
            String.class)
    );
  }

  /**
   * Test if the phone is duplicate before registering.
   */
  @Test
  public void testDuplicatePhoneBeforeRegister() {
    assertEquals("duplicate phone", DUPLICATE_STATE,
        restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User(PHONE, null, null),
            String.class)
    );
  }

  /**
   * Check if the phone is valid before registering.
   */
  @Test
  public void testValidPhoneBeforeRegister() {
    assertEquals("valid phone", VALID_STATE,
        restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User("12345623477", null, null),
            String.class)
    );
  }

  /**
   * Check if the register is succeed.
   */
  @Test
  public void testRegister() {
    assertEquals("valid register", VALID_STATE,
        restTemplate.postForObject(
            REGISTER_URL,
                new User("1234", "nicolemayer", "123456"),
            String.class));
    assertEquals("valid register", FAIL_STATE,
            restTemplate.postForObject(
                    REGISTER_URL,
                    new User("123456", "nicolemayer", "123456"),
                    String.class)
    );
  }
}
