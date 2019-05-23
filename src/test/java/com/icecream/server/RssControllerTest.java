package com.icecream.server;

import com.icecream.server.client.LoginResponse;
import com.icecream.server.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
  private static final String ALL_CHANNELS_URL = MAIN_URL + "list/all/feeds";
  private static final String ARTICILES_FOR_ONE_CHANNEL_URL = MAIN_URL + "list/feed/%d/articles";
  private static final String ALL_CHANNELS_ARTICILES_URL = MAIN_URL + "list/feed/all/articles";

  private static final String ALL_SUBSCRIBED_ARTICILES_URL = MAIN_URL + "/list/user/all/articles";
  private static final String ONE_ARTICLE_URL = MAIN_URL + "list/article/%d";
  private static final String ADD_CHANNEL_URL = MAIN_URL + "addChannel";
  private static final String DELETE_CHANNEL_URL = MAIN_URL + "deleteChannel";

  private static final String COLLECTED_ARTICLES_URL = MAIN_URL + "/list/like/articles";


  private static final String LIKE_ARTICLE_URL = MAIN_URL + "/like/article/%d";
  private static final String UNLIKE_ARTICLE_URL = MAIN_URL + "/unlike/article/%d";
  private static final String FRESH_RECORD_URL = MAIN_URL + "/freshRecords";

  private static final String GET_MP3_URL = MAIN_URL + "/list/record_mp3/%d";
  private static final String GET_MP3_INFO_URL = MAIN_URL + "/list/record_info/%d";

  private transient String token;
  private static final String MESSAGE = "message";

  /**
   * Before testing, get the token of the user.
   */
  @Before
  public void before() throws Exception {
    LoginResponse loginResponse = restTemplate.postForObject(
            LOGIN_URL,
            new User("12345", null, "123456"),
            LoginResponse.class);
    token = "?token=" + loginResponse.getToken();
  }


  /**
    * Test for welcome response.
    */
  @Test
  public void testHello() throws Exception {
    String response = restTemplate.getForObject(MAIN_URL,String.class);
    assertEquals("Just the welcome page", "welcome to icecream server", response);
  }

  /**
   * Test for get channel list for current user.
   */
  @Test
  public void testGetChannelListForCurrentUser() throws Exception {
    String response = restTemplate.getForObject(MY_CHANNELS_URL+token, String.class);
    JSONObject jsonObject = new JSONObject(response);
    JSONArray list = jsonObject.getJSONArray("data");

    assertEquals("subscribed channel number", 4, list.length());
    List<String> urls = new ArrayList<>();
    urls.add("http://www.zhihu.com/rss");
    urls.add("http://www.feng.com/rss.xml");
    urls.add("https://36kr.com/feed");
    urls.add("http://www.geekpark.net/rss");
    for (int i = 0; i < list.length(); i++) {
      JSONObject channel_info = list.getJSONObject(i);
      if(!urls.contains(channel_info.getString("url"))){
        fail("The subscribed channel doesn't exist");
      }
    }
  }

  /**
   * Test for get all channel list.
   */
  @Test
  public void testGetAllChannelList() throws Exception {
    List<LinkedHashMap> list = restTemplate.getForObject(ALL_CHANNELS_URL, ArrayList.class);

    assertEquals("subscribed channel number", 4, list.size());
    List<String> urls = new ArrayList<>();
    urls.add("http://www.zhihu.com/rss");
    urls.add("http://www.feng.com/rss.xml");
    urls.add("https://36kr.com/feed");
    urls.add("http://www.geekpark.net/rss");
    for (LinkedHashMap linkedHashMap: list) {
      String channel_info = (String) linkedHashMap.get("url");
      if(!urls.contains(channel_info)){
        fail("The subscribed channel doesn't exist");
      }
    }
  }

  /**
   * Test for get newest articles from one channel.
   */
  @Test
  public void testGetNewestArticlesFromOneFeed() throws Exception {
    String url = String.format(ARTICILES_FOR_ONE_CHANNEL_URL+token, 1000); //can't have a channel with id 1000
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("feed not find", jsonObject.getString(MESSAGE));

    url = String.format(ARTICILES_FOR_ONE_CHANNEL_URL+token,1); //must have a channel with id 1
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
  }

  /**
   * Test for getting newest articles from all subscribedchannels.
   */
  @Test
  public void testGetNewestArticles() throws Exception {
    String response = restTemplate.getForObject(ALL_SUBSCRIBED_ARTICILES_URL+token, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
    assertEquals("2", jsonObject.getString("msgCode"));
  }

  /**
   * Test for getting newest articles from all channels.
   */
  @Test
  public void testGetNewestArticles2() throws Exception {
    String response = restTemplate.getForObject(ALL_CHANNELS_ARTICILES_URL, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
    assertEquals("2", jsonObject.getString("msgCode"));
  }

  /**
   * Test for getting one article.
   */
  @Test
  public void testGetOneArticle() throws Exception {
    String url = String.format(ONE_ARTICLE_URL+token, 100);
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("find article successfully", jsonObject.getString(MESSAGE));

    url = String.format(ONE_ARTICLE_URL+token, 1); // article must have sth with id 100, not 1
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("article not find", jsonObject.getString(MESSAGE));
  }

  /**
   * Test for subscribing channel.
   */
  @Test
  public void testSubscribeChannel() throws Exception {
    String response = restTemplate.getForObject(ADD_CHANNEL_URL+token+"&url=http://www.geekpark.net/rss", String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("add succeed", jsonObject.getString(MESSAGE));

    response = restTemplate.getForObject(ADD_CHANNEL_URL+token+"&url=http://www.geekpark.net/rss", String.class);
    jsonObject = new JSONObject(response);
    assertEquals("add failed", jsonObject.getString(MESSAGE));
  }

  /**
   * Test for deleting channel.
   */
  @Test
  public void testDeleteChannel() throws Exception {
    String url = DELETE_CHANNEL_URL+token+"&url=http://sdklafjladsfjlasf";
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("delete failed", jsonObject.getString(MESSAGE));

    url =  DELETE_CHANNEL_URL+token+"&url=http://www.geekpark.net/rss";
    response = restTemplate.getForObject(url, String.class);
    jsonObject = new JSONObject(response);
    assertEquals("delete succeed", jsonObject.getString(MESSAGE));
  }

  /**
   * Test for collected channels.
   */
  @Test
  public void testGetCollectedArticles() throws Exception {
    String url = COLLECTED_ARTICLES_URL+token;
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
    assertEquals("2", jsonObject.getString("msgCode"));
  }

  /**
   * Test for collecting an article.
   */
  @Test
  public void testLikeArticle() throws Exception {
    String url = String.format(LIKE_ARTICLE_URL+token, 100);
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("like succeed", jsonObject.getString(MESSAGE));
    assertEquals(3, jsonObject.getInt("msgCode"));
  }

  /**
   * Test for uncollecting an article.
   */
  @Test
  public void testUnlikeArticle() throws Exception {
    String url = String.format(UNLIKE_ARTICLE_URL+token, 100);
    String response = restTemplate.getForObject(url, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("unlike succeed", jsonObject.getString(MESSAGE));
    assertEquals(3, jsonObject.getInt("msgCode"));
  }

  /**
   * Test for refreshing the records.
   */
  @Test
  public void testFreshRecords() throws Exception {
    String response = restTemplate.getForObject(FRESH_RECORD_URL, String.class);
    JSONObject jsonObject = new JSONObject(response);
    assertEquals("succeed", jsonObject.getString(MESSAGE));
    assertEquals(0, jsonObject.getInt("msgCode"));
  }


  /**
   * Test for getting the mp3.
   */
  @Test
  public void testGetOneRecordMp3() throws Exception {
    String url = String.format(GET_MP3_URL, 100);
    try{
      restTemplate.getForObject(url, String.class);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
  }
  /**
   * Test for getting the mp3 info.
   */
  @Test
  public void testGetOneRecordInfo() throws Exception {
    String url = String.format(GET_MP3_INFO_URL, 100);
    try{
      restTemplate.getForObject(url, String.class);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
  }

}
