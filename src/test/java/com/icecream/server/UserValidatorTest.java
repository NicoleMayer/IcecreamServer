package com.icecream.server;

import static org.junit.Assert.assertEquals;

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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

/** 
* UserValidator Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 20, 2019</pre> 
* @version 1.0 
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IceCreamServerApplication.class)
public class UserValidatorTest {
  @Autowired
  private transient UserValidator userValidator;

  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }
  /**
   *
   * Method: registerValidate(String phoneNumber)
   *
   */
  @Test
  public void testRegisterValidate() throws Exception {
    assertEquals("valid", UserValidator.ValidationResult.Valid, userValidator.registerValidate("123454564"));

    assertEquals("test duplicate phone", UserValidator.ValidationResult.DuplicatePhoneNumber, userValidator.registerValidate("12345456"));
  }

  /**
  *
  * Method: loginValidate(String phoneNumber, String password)
  *
  */
  @Test
  public void testLoginValidate() throws Exception {
    assertEquals("test no such user", UserValidator.ValidationResult.NoSuchUser, userValidator.loginValidate("", ""));
    assertEquals("test no such user", UserValidator.ValidationResult.NoSuchUser, userValidator.loginValidate("1245455", ""));

    assertEquals("wrong password", UserValidator.ValidationResult.WrongPassword, userValidator.loginValidate("12345456", "12454556"));
    assertEquals("valid", UserValidator.ValidationResult.Valid, userValidator.loginValidate("12345456", "124354556"));
  }


} 
