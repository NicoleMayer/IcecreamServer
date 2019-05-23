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

import static org.assertj.core.api.Java6Assertions.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * RssFeedServiceImpl Tester.
 *
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

  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }

  /**
  * Test for adding a channel.
  */
  @Test
  public void testAddChannelSuccess() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertTrue(rssFeedService.addChannel("http://www.feng.com/rss.xml", user));
    rssFeedService.deleteChannel("http://www.feng.com/rss.xml", user);
  }

  /**
   * Test for adding a channel.
   */
  @Test
  public void testAddChannelFail() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertFalse(rssFeedService.addChannel("http://www.aaaa.com/feed", user));
  }

  /**
   * Test for deleting a channel.
   */
  @Test
  public void testDeleteChannelSuccess() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    rssFeedService.addChannel("https://36kr.com/feed", user);
    assertTrue(rssFeedService.deleteChannel("https://36kr.com/feed", user));
  }

  /**
   * Test for deleting a channel.
   */
  @Test
  public void testDeleteChannelFail() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertFalse(rssFeedService.deleteChannel("https://aaaaa.com/feed", user));
  }

  /**
   *  Test for finding a rss channel by its id.
   */
  @Test
  public void testFindById() throws Exception {
    try{
      rssFeedService.findById(1);
    } catch (Exception e)
    {
      fail("method FindById should not fail");
    }

    try{
      rssFeedService.findById(100);
    } catch (Exception e)
    {
      fail("method FindById should not fail");
    }

  }


  /**
   * Test for crawling articles.
   */
  @Test
  public void testCrawlArticles() throws Exception {
    try{
      rssFeedService.crawlArticles("http://www.ifanr.com/feed");
    } catch (Exception e)
    {
      fail("method crawlArticles should not fail");
    }

    try{
      rssFeedService.crawlArticles("http://www.feng.com/rss.xml");
    } catch (Exception e)
    {
      fail("method crawlArticles should not fail");
    }
  }

  /**
   * Test for adding articles.
   */
  @Test
  public void testAddArticles() throws Exception {
    RssFeed rssFeed = rssFeedRepository.findByUrl("https://36kr.com/feed");
    try{
      rssFeedService.addArticles(rssFeed);
    } catch (Exception e)
    {
      fail("method addArticles should not fail");
    }
    rssFeed = rssFeedRepository.findByUrl("http://www.feng.com/rss.xml");
    try{
      rssFeedService.addArticles(rssFeed);
    } catch (Exception e)
    {
      fail("method addArticles should not fail");
    }
  }

}
