package com.icecream.server;

import static org.junit.Assert.assertEquals;

import com.icecream.server.entity.User;
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
  private RestTemplate restTemplate = new RestTemplate();

  private static String PROTOCOL = "http";
  private static String HOST = "localhost";
  private static String PORT = "8080";
  private static String PRE_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

  private static String LOGIN_URL = PRE_URL + "login";
  private static String REGISTER_URL = PRE_URL + "register";
  private static String BEFORE_REGISTER_URL = PRE_URL + "before-register";

  @Before
  public void before() throws Exception {

  }

  @After
  public void after() throws Exception {
  }

  @Test
  public void testLoginValid() throws Exception {
    assertEquals("{\"state\":\"Valid\"}",
        testLoginUtils("12345623456", "12345656"));
  }

  @Test
  public void testLoginInvalid() throws Exception {
    assertEquals("{\"state\":\"NoSuchUser\"}",
        testLoginUtils("1234562456", "1235656"));
  }

  @Test
  public void testBeforeRegister() throws Exception {
    assertEquals("{\"state\":\"DuplicatePhoneNumber\"}",
        testRegisterUtils(BEFORE_REGISTER_URL, "12345623467", null, null));
    assertEquals("{\"state\":\"Valid\"}",
        testRegisterUtils(BEFORE_REGISTER_URL, "12345623477", null, null));
  }

  @Test
  public void testRegister() throws Exception {
    assertEquals("{\"state\":\"Valid\"}", testRegisterUtils(REGISTER_URL, "12345623456",
        "nicolemayer", "12345656"));
    assertEquals("{\"state\":\"Valid\"}", testRegisterUtils(REGISTER_URL, "12345623467",
        "nicolemayer", "12345656"));
  }

  public String testLoginUtils(String phone, String password) throws Exception {
//    HttpHeaders headers = new HttpHeaders();
//    MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
//    mapInfo.add("phone", phone);
//    mapInfo.add("password", password);
//    HttpEntity<MultiValueMap<String, String>> request
//            = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);
    return restTemplate.postForObject(LOGIN_URL, new User(phone, null, password), String.class);
  }

  public String testRegisterUtils(String url, String phone, String username, String password)
      throws Exception {
//    HttpHeaders headers = new HttpHeaders();
//    MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
//    mapInfo.add("phone", phone);
//    if (url.equals(REGISTER_URL)){
//      mapInfo.add("username", username);
//      mapInfo.add("password", password);
//    }
//
//    HttpEntity<MultiValueMap<String, String>> request
//            = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);
    return restTemplate.postForObject(url, new User(phone, username, password), String.class);
  }
}
