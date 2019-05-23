package com.icecream.server;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

  private static final String LOGIN_URL = MAIN_URL + "signin";
  private static final String MY_CHANNELS_URL = MAIN_URL + "list/feeds";
  private static final String ARTICILES_FOR_ONE_CHANNEL_URL = MAIN_URL + "list/feed/%d/articles";
  private static final String ALL_SUBSCRIBED_ARTICILES_URL = MAIN_URL + "list/feed/all/articles";
  private static final String ONE_ARTICLE_URL = MAIN_URL + "list/article/%d";
  private static final String ADD_CHANNEL_URL = MAIN_URL + "addChannel";
  private static final String DELETE_CHANNEL_URL = MAIN_URL + "deleteChannel";

  private transient String token;
  private static final String MESSAGE = "message";

  /**
   * Before testing, get the token of the user
   */
  @Before
  public void before() throws Exception {
    LoginResponse loginResponse = restTemplate.postForObject(
            LOGIN_URL,
            new User("1234", null, "123456"),
            LoginResponse.class);
    token = "?token=" + loginResponse.getToken();
  }


  /**
    * Test for welcome response
    */
  @Test
  public void testHello() throws Exception {
    String response = restTemplate.getForObject(MAIN_URL,String.class);
    assertEquals("Just the welcome page", "welcome to icecream server", response);
  }

  /**
   * Test for get channel list for current user
   */
  @Test
  public void testGetChannelListForCurrentUser() throws Exception {
    String response = restTemplate.getForObject(MY_CHANNELS_URL+token, String.class);
    JSONObject jsonObject = new JSONObject(response);
    JSONArray list = jsonObject.getJSONArray("data");

    assertEquals("subscribed channel number", 4, list.length());
    List<String> urls = new ArrayList<>();
    urls.add("http://www.ifanr.com/feed");
    urls.add("http://www.feng.com/rss.xml");
    urls.add("https://36kr.com/feed");
    urls.add("http://www.geekpark.net/rss");
    for (int i = 0; i < list.length(); i++) {
      JSONObject channel_info = list.getJSONObject(i);
      System.out.println("channel name is "+channel_info.getString("channelName"));
      System.out.println("category is "+channel_info.getString("category"));
      System.out.println("url is "+channel_info.getString("url"));
      if(!urls.contains(channel_info.getString("url"))){
        fail("The subscribed channel doesn't exist");
      }
    }
  }

  /**
   * Test for get newest articles from one channel
   */
  @Test
  public void testGetNewestArticlesFromOneFeed() throws Exception {
    String url = String.format(ARTICILES_FOR_ONE_CHANNEL_URL+token, 1);
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("feed not find", jsonObject.getString(MESSAGE));

    url = String.format(ARTICILES_FOR_ONE_CHANNEL_URL+token, 23);
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
  }

  /**
  * Test for getting newest articles from all channel
  */
  @Test
  public void testGetNewestArticles() throws Exception {
    String response = restTemplate.getForObject(ALL_SUBSCRIBED_ARTICILES_URL+token, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
    assertEquals("2", jsonObject.getString("msgCode"));
  }

  /**
  * Test for getting one article
  */
  @Test
  public void testGetOneArticle() throws Exception {
    String url = String.format(ONE_ARTICLE_URL+token, 293);
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("article find succeed", jsonObject.getString(MESSAGE));

    url = String.format(ONE_ARTICLE_URL+token, 1);
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("article not find", jsonObject.getString(MESSAGE));
  }

  /**
  * Test for subscribing channel
  */
  @Test
  public void testSubscribeChannel() throws Exception {
    String response = restTemplate.getForObject(ADD_CHANNEL_URL+token+"&url=http://www.ifanr.com/feed", String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("add succeed", jsonObject.getString(MESSAGE));

    response = restTemplate.getForObject(ADD_CHANNEL_URL+token+"&url=http://www.ifanr.com/feed", String.class);
    jsonObject = new JSONObject(response);
    assertEquals("add failed", jsonObject.getString(MESSAGE));
  }

  /**
   * Test for deleting channel
   */
  @Test
  public void testDeleteChannel() throws Exception {
    String url = DELETE_CHANNEL_URL+token+"&url=http://sdklafjladsfjlasf";
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("channel not find", jsonObject.getString(MESSAGE));

    url =  DELETE_CHANNEL_URL+token+"&url=http://www.ifanr.com/feed";
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("delete succeed", jsonObject.getString(MESSAGE));
  }


}
