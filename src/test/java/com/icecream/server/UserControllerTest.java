package com.icecream.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * UserController Tester.
 *
 * @author nicolemayer
 * @version 1.0
 * @since <pre>Apr 17, 2019</pre>
 */
public class UserControllerTest {

  @Autowired
  private transient RestTemplate restTemplate = new RestTemplate();

  private static String PROTOCOL = "http";
  private static String HOST = "localhost";
  private static String PORT = "8080";
  private static String PRE_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

  private static String LOGIN_URL = PRE_URL + "login";
  private static String REGISTER_URL = PRE_URL + "register";
  private static String BEFORE_REGISTER_URL = PRE_URL + "beforeregister";

  private static String VALID_STATE = "{\"state\":\"Valid\"}";
  private static String DUPLICATE_STATE = "{\"state\":\"DuplicatePhoneNumber\"}";
  private static String NOUSER_STATE = "{\"state\":\"NoSuchUser\"}";

  @Before
  public void before() throws Exception {

  }

  @After
  public void after() throws Exception {
  }

  @Test
  public void testLogin() throws Exception {
    assertEquals(testLoginUtils("12345623456", "12345656"), VALID_STATE);
    assertEquals(testLoginUtils("1234562456", "1235656"), NOUSER_STATE);
  }


  public String testLoginUtils(String phone, String password) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
    mapInfo.add("phone", phone);
    mapInfo.add("password", password);
    HttpEntity<MultiValueMap<String, String>> request
            = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);

    return restTemplate.postForObject(LOGIN_URL, request, String.class);
  }


  @Test
  public void testRegister() throws Exception {
    assertEquals(testRegisterUntils(REGISTER_URL, "12345623456",
            "nicolemayer", "12345656"), VALID_STATE);
    assertEquals(testRegisterUntils(REGISTER_URL, "12345623467",
            "nicolemayer", "12345656"), VALID_STATE);

  }

  @Test
  public void testBeforeRegister() throws Exception {
    assertEquals(testRegisterUntils(BEFORE_REGISTER_URL, "12345623467", null, null), DUPLICATE_STATE);
    assertEquals(testRegisterUntils(BEFORE_REGISTER_URL, "12345623477", null, null), VALID_STATE);
  }

  public String testRegisterUntils(String url, String phone, String username, String password)
          throws Exception {
    HttpHeaders headers = new HttpHeaders();
    MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
    mapInfo.add("phone", phone);
    if (url.equals(REGISTER_URL)){
      mapInfo.add("username", username);
      mapInfo.add("password", password);
    }

    HttpEntity<MultiValueMap<String, String>> request
            = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);

    return restTemplate.postForObject(url, request, String.class);
  }


}
