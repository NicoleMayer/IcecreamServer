package com.icecream.server;

import com.icecream.server.service.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


/** 
 * UserServiceImpl Tester.
 *
 * @author nicolemayer
 * @version 1.0
 * @since <pre>Apr 19, 2019</pre>
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IceCreamServerApplication.class)
public class UserServiceImplTest {
  @Autowired
  private transient UserService userService;

  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }


  /**
  * Check if the phone number has been used.
  */
  @Test
  public void testFindByPhoneNumber() throws Exception {
    assertEquals("test find results", true, testFindByPhoneNumberUtils("12345456"));
    assertEquals("test find results", false, testFindByPhoneNumberUtils("1234546"));
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


} 
