package com.icecream.server;

import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.User;
import com.icecream.server.service.UserService;
import com.icecream.server.service.UserServiceImpl;
import com.icecream.server.service.UserValidator;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 20, 2019</pre> 
* @version 1.0 
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
  *
  * Method: save(User user)
  *
  */
  @Test
  public void testValidSave() throws Exception {
    try{
      userService.save(new User("12345456","niiiiiii","1243545566"));
      userService.save(new User("12345453","niiiii1i","124354556"));
    }catch (Exception e){
      fail("method should not fail!");
    }
  }

  /**
  *
  * Method: check(User user, String password)
  *
  */
  @Test
  public void testCheck() throws Exception {
    User user = new User("12345456","niiiiiii","124354556");
    assertEquals("test password equal", true, userService.check(user, "1243545566"));
    assertEquals("test password equal", false, userService.check(user, "12435456"));
  }

  /**
  *
  * Method: findByPhoneNumber(String phoneNumber)
  *
  */
  @Test
  public void testFindByPhoneNumber() throws Exception {
    assertEquals("test find results", true, testFindByPhoneNumberUtils("12345456"));
    assertEquals("test find results", false, testFindByPhoneNumberUtils("1234546"));
  }

  public boolean testFindByPhoneNumberUtils(String phone) throws Exception {
    if(userService.findByPhoneNumber(phone)!=null)
      return true;
    else
      return false;
  }


} 
