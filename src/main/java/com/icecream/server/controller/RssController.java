package com.icecream.server.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.rssfeed.ArticleService;
import com.icecream.server.service.rssfeed.RssFeedService;
import com.icecream.server.service.user.UserSecurityService;
import com.icecream.server.service.user.UserService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;


@RestController
public class RssController {
  private UserService userService;

  private RssFeedService rssFeedService;

  private ArticleService articleService;

  private UserSecurityService userSecurityService;

  private static final Logger LOGGER = Logger.getLogger(UserController.class);

  public RssController(UserService userService, RssFeedService rssFeedService, ArticleService articleService, UserSecurityService userSecurityService) {
    this.userService = userService;
    this.rssFeedService = rssFeedService;
    this.articleService = articleService;
    this.userSecurityService = userSecurityService;
  }

  @RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String hello() {
    return "hello WORLD";
  }

  @RequestMapping(value = {"/list/feeds"}, method = RequestMethod.GET)
  public String getChannelListForCurrentUser() {
//    User user = userService.getCurrentUser();
    User user = userService.findByPhoneNumber("123456");
    try {
      return new JSONObject().put("channels", user.getRssFeedEntities()).toString();
    } catch (JSONException e) {
      LOGGER.debug("json sexception");
      return "json fail";
    }
  }

  @RequestMapping(value = {"/list/articles"}, method = RequestMethod.GET)
  public String getNewestArticles() {
    User user = userService.getCurrentUser();
    articleService.find30NewestArticlesFromManyFeeds(user.getRssFeedEntities());
    try {
      return new JSONObject().put("channels", user.getRssFeedEntities()).toString();
    } catch (JSONException e) {
      LOGGER.debug("json sexception");
      return "json fail";
    }
  }

  @RequestMapping(value = {"/list/article/{id}"}, method = RequestMethod.GET)
  public String getOneArticle(@PathVariable("id") Long id) throws JSONException{
    Article article = articleService.findById(id);
    JSONObject msg = new JSONObject();
    msg.put("title", article.getTitle());
    msg.put("link", article.getLink());
    msg.put("publishtime", article.getPublishedTime());
    msg.put("content", article.getDescription());
    return msg.toString();
  }

//  @GetMapping(value = "/article/{id}")
//  @RequestMapping(value="/article/{id}",method=RequestMethod.GET)
//  public String getRssFeedList(@PathVariable("id") Integer id, String phoneNumber) throws JSONException {
//    ArticleService articleService = new ArticleServiceImpl();
//    List<Article> articles = articleService.find10NewestEntries();
//    Article article = articles.get(id);


//  @GetMapping(value = "/channellist")
//  public String getRssFeedList(String phoneNumber) throws JSONException {
//
//    User user = userService.findByPhoneNumber(phoneNumber);
//    user = userService.findAllChannels(user);
//    List<RssFeed> rssFeedList = user.getRssFeedEntities();
//    List<String> rssFeedName = new ArrayList<>();
//    for (RssFeed rssFeed : rssFeedList) {
//      rssFeedName.add(rssFeed.getName());
//    }
//    JSONObject msg = new JSONObject().put("channels", rssFeedName);
//    return msg.toString();
//  }

//  //TODO 没有测试
//  @PostMapping(path = "/user/{uid}/addChannel")
//  public String subscribeChannel(RssFeed rssFeedEntity, String phoneNumber) {
//    rssFeedService.addChannel(rssFeedEntity, phoneNumber);
//    return "addChannel channel succeed";
//  }
//
//  //TODO 没有测试 这里没有写针对哪个用户删除？？？？？
//  @PostMapping(path = "/deleteChannel")
//  public String deleteChannel(@PathVariable Long id) {
//    RssFeed rssFeedEntity = rssFeedService.findOne(id);
//    rssFeedService.delete(rssFeedEntity);
//    return "delet channel succeed";
//  }
}
