package com.icecream.server;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/** 
* RssController Tester.
* @author nicolemayer
*/ 
public class RssControllerTest {

  private final transient RestTemplate restTemplate = new RestTemplate();

  private static final String PROTOCOL = "http";
  private static final String HOST = "localhost";
  private static final String PORT = "8080";
  private static final String MAIN_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

  private static final String MY_CHANNELS_URL = MAIN_URL + "list/feeds";
  private static final String REGISTER_URL = MAIN_URL + "signup";
  private static final String BEFORE_REGISTER_URL = MAIN_URL + "before-register";
  /**
  *
  * Method: hello()
  *
  */
  @Test
  public void testHello() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: getChannelListForCurrentUser(String token)
  *
  */
  @Test
  public void testGetChannelListForCurrentUser() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: getNewestArticlesFromOneFeed(@PathVariable("id") Long id, String token)
  *
  */
  @Test
  public void testGetNewestArticlesFromOneFeed() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: getNewestArticles(String token)
  *
  */
  @Test
  public void testGetNewestArticles() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: getOneArticle(@PathVariable("id") Long id, String token)
  *
  */
  @Test
  public void testGetOneArticle() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: subscribeChannel(String token, RssFeed rssFeedEntity)
  *
  */
  @Test
  public void testSubscribeChannel() throws Exception {
  //TODO: Test goes here...
  }

  /**
  *
  * Method: deleteChannel(String token, Long channel_id)
  *
  */
  @Test
  public void testDeleteChannel() throws Exception {
  //TODO: Test goes here...
  }


} 
