package com.icecream.server;

import static org.junit.Assert.assertEquals;

import com.icecream.server.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

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

  private static final String LOGIN_URL = MAIN_URL + "login";
  private static final String REGISTER_URL = MAIN_URL + "register";
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
   * @description: test when the login is valid
   * @param []
   * @return void
   * @author NicoleMayer
   * @date 2019-04-20
   */
  @Test
  public void testLoginValid() {
    assertEquals("valid login", VALID_STATE,
        restTemplate.postForObject(
            LOGIN_URL,
            new User(PHONE, null, "12345656"),
            String.class)
    );
  }

  /**
   * @description: test when the login is invalid
   * @param []
   * @return void
   * @author NicoleMayer
   * @date 2019-04-20
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
   * @description: test if the phone is duplicate before registering
   * @param
   * @return void
   * @author NicoleMayer
   * @date 2019-04-20
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
   * @description: check if the phone is valid before registering
   * @param []
   * @return void
   * @author NicoleMayer
   * @date 2019-04-20
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
   * @description: check if the register is succeed
   * @param []
   * @return void
   * @author NicoleMayer
   * @date 2019-04-20
   */
  @Test
  public void testRegister() {
    assertEquals("valid register", VALID_STATE,
        restTemplate.postForObject(
            REGISTER_URL,
                new User(PHONE, "nicolemayer", "12345656"),
            String.class)
    );
    assertEquals("valid register", FAIL_STATE ,
            restTemplate.postForObject(
                    REGISTER_URL,
                    new User(PHONE, "nicolemayer", "12345656"),
                    String.class)
    );
  }
}
