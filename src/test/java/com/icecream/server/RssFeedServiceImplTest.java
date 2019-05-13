package com.icecream.server;

import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.rssfeed.RssFeedService;
import com.icecream.server.service.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** 
 * RssFeedServiceImpl Tester.
 * @author nicolemayer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IceCreamServerApplication.class)
public class RssFeedServiceImplTest {

  @Autowired
  private transient UserService userService;

  @Autowired
  private transient RssFeedService rssFeedService;

  @Autowired
  private transient RssFeedRepository rssFeedRepository;

  private final transient RestTemplate restTemplate = new RestTemplate();

  private String token;

  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }

  /**
  * Test for adding a channel
  */
  @Test
  public void testAddChannelSuccess() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertEquals(true, rssFeedService.addChannel(new RssFeed("https://36kr.com/feed"), user));
  }

  /**
   * Test for adding a channel
   */
  @Test
  public void testAddChannelFail() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertEquals(false, rssFeedService.addChannel(new RssFeed("http://www.aaaa.com/feed"), user));
  }

  /**
  * Test for deleting a channel
  */
  @Test
  public void testDeleteChannel() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    RssFeed rssFeed = rssFeedRepository.findByUrl("https://36kr.com/feed");
    System.out.println("rssFeed is "+ rssFeed);
    boolean res = rssFeedService.deleteChannel(rssFeed, user);
    assertEquals(true, res);
  }

  /**
  *  Test for finding a rss channel by its id
  */
  @Test
  public void testFindById() throws Exception {
    try{
      rssFeedService.findById(1);
    } catch (Exception e)
    {
      fail("method should not fail");
    }

    try{
      rssFeedService.findById(100);
    } catch (Exception e)
    {
      fail("method should not fail");
    }

  }


  /**
  *
  * Method: crawlArticles(Source source)
  *
  */
  @Test
  public void testCrawlArticles() throws Exception {
    try{
      rssFeedService.crawlArticles("http://www.ifanr.com/feed");
    } catch (Exception e)
    {
      fail("method should not fail");
    }

    try{
      rssFeedService.crawlArticles("http://www.feng.com/rss.xml");
    } catch (Exception e)
    {
      fail("method should not fail");
    }
  }

} 
