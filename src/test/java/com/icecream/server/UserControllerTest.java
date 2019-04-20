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

  private final RestTemplate restTemplate = new RestTemplate();

  private static final String PROTOCOL = "http";
  private static final String HOST = "localhost";
  private static final String PORT = "8080";
  private static final String MAIN_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

  private static final String LOGIN_URL = MAIN_URL + "login";
  private static final String REGISTER_URL = MAIN_URL + "register";
  private static final String BEFORE_REGISTER_URL = MAIN_URL + "before-register";

  @Before
  public void before() {

  }

  @After
  public void after() {
  }

  @Test
  public void testLoginValid() {
    assertEquals("valid login", "{\"state\":\"Valid\"}",
        restTemplate.postForObject(
            LOGIN_URL,
            new User("12345623456", null, "12345656"),
            String.class)
    );
  }

  @Test
  public void testLoginInvalid() {
    assertEquals("invalid login", "{\"state\":\"NoSuchUser\"}",
        restTemplate.postForObject(
            LOGIN_URL,
            new User("1234562456", null, "1235656"),
            String.class)
    );
  }

  @Test
  public void testDuplicatePhoneBeforeRegister() {
    assertEquals("duplicate phone", "{\"state\":\"DuplicatePhoneNumber\"}",
        restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User("12345623467", null, null),
            String.class)
    );
  }

  @Test
  public void testValidPhoneBeforeRegister() {
    assertEquals("valid phone", "{\"state\":\"Valid\"}",
        restTemplate.postForObject(
            BEFORE_REGISTER_URL,
            new User("12345623477", null, null),
            String.class)
    );
  }

  @Test
  public void testRegister() {
    assertEquals("valid register", "{\"state\":\"Valid\"}",
        restTemplate.postForObject(
            REGISTER_URL,
            new User("12345623456", "nicolemayer", "12345656"),
            String.class)
    );
  }
}
