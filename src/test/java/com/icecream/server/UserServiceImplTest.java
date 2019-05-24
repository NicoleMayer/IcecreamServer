package com.icecream.server;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.entity.User;
import com.icecream.server.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * UserServiceImpl Tester.
 *
 * @author nicolemayer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IceCreamServerApplication.class)
public class UserServiceImplTest {

  @Autowired
  private transient UserService userService;

  private final transient RestTemplate restTemplate = new RestTemplate();

  /**
   * Test the phone number exists.
   */
  @Test
  public void testFindByPhoneNumber() throws Exception {
    assertEquals("test find by phone number", true, testFindByPhoneNumberUtils("1234"));
    assertEquals("test find by phone number", false, testFindByPhoneNumberUtils("12345896345"));
  }

  /**
   * Until method for testFindByPhoneNumber, return true if finds one.
   */
  public boolean testFindByPhoneNumberUtils(String phone) throws Exception {
    if (userService.findByPhoneNumber(phone) != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Test the user id exists.
   */
  @Test
  public void testFindById() throws Exception {
    assertEquals("test find by id", true, testFindByIdUtils((long)1));
    assertEquals("test find by id", false, testFindByIdUtils((long)1000000000));
  }

  /**
   * Until method for testFindById, return true if finds one.
   */
  public boolean testFindByIdUtils(Long id) throws Exception {
    if (userService.findById(id).isPresent()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Test the user login.
   */
  @Test
  public void testLoginUser() throws Exception {
    LoginResponse loginResponse = userService.loginUser(new User("1234", null, "123456"));
    assertEquals("valid login", "login succeed", loginResponse.getMessage());
    loginResponse = userService.loginUser(new User("1234562456", null, "1235656"));
    assertEquals("invalid login", "can't find phone number", loginResponse.getMessage());
    loginResponse = userService.loginUser(new User("1234", null, "1235656"));
    assertEquals("invalid login", "wrong password", loginResponse.getMessage());
  }

  /**
   * Test the user register.
   */
  @Test
  public void testRegisterUser() throws Exception {
    NormalResponse normalResponse = userService.registerUser(new User("12345789", "nicolemayer", "123456"));
    assertEquals("valid register", "register succeed", normalResponse.getMessage());

    normalResponse = userService.registerUser(new User("12345896345", "nicolemayer", "123456"));
    assertEquals("valid register", "register succeed", normalResponse.getMessage());

  }


  /**
   * Test verify the token.
   */
  @Test
  public void testVerifyToken() throws Exception {
    LoginResponse loginResponse = restTemplate.postForObject(
            "http://localhost:8081/signin",
            new User("12345", null, "123456"),
            LoginResponse.class);
    String token = loginResponse.getToken();
    if(userService.verifyToken(token) == null){
      fail("id shouldn't be null");
    }

    if(userService.verifyToken("123445465465") != null){
      fail("id shouldn be null");
    }
  }

}
