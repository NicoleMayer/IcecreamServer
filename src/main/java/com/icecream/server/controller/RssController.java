package com.icecream.server.controller;

import com.icecream.server.client.ArticleResponse;
import com.icecream.server.client.ArticlesResponse;
import com.icecream.server.client.FeedsResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.rssfeed.ArticleService;
import com.icecream.server.service.rssfeed.RssFeedService;
import com.icecream.server.service.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is a rest controller for rss subscribe
 * @author NicoleMayer
 */
@RestController
public class RssController {

  private UserService userService;

  private RssFeedService rssFeedService;

  private ArticleService articleService;

  public RssController(UserService userService, RssFeedService rssFeedService, ArticleService articleService) {
    this.userService = userService;
    this.rssFeedService = rssFeedService;
    this.articleService = articleService;
  }

  /**
   * Welcome page
   * @return Welcome messgae.
   */
  @RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String hello() {
    return "welcome to icecream server";
  }

  /**
   * User's subscribed channel
   * @param token verification for user
   * @return FeedsResponse
   */
  @RequestMapping(value = {"/list/feeds"}, method = RequestMethod.GET)
  public FeedsResponse getChannelListForCurrentUser(String token) {
    Long id = userService.verifyToken(token);
    if (id == null) {
      return new FeedsResponse("wrong token", 0, new HashSet<>());
    }
    User user = userService.findById(id).get();
    if (user == null) {
      return new FeedsResponse("user not find", 1, new HashSet<>());
    }
    return new FeedsResponse("succeed", 2, user.getRssFeedEntities());
  }

  /**
   * Articles for a subscribed channel
   * @param token verification for user
   * @return ArticlesResponse
   */
  @RequestMapping(value = {"/list/feed/{id}/articles"}, method = RequestMethod.GET)
  public ArticlesResponse getNewestArticlesFromOneFeed(@PathVariable("id") Long id, String token) {
    Long user_id = userService.verifyToken(token);
    if (user_id == null) {
      return new ArticlesResponse("wrong token", 0, new ArrayList<>());
    }
    User user = userService.findById(user_id).get();
    if (user == null) {
      return new ArticlesResponse("user not find", 1, new ArrayList<>());
    }

    RssFeed rssFeed = rssFeedService.findById(id).get();
    if (rssFeed == null) {
      return new ArticlesResponse("feed not find", 2, new ArrayList<>());
    }
    List<Article> articles = articleService.find30NewestArticlesFromOneFeed(rssFeed);
    for (Article article: articles) {
      article.setDescription(article.getDescription().substring(0,50));
    }
    return new ArticlesResponse("succeed", 3, articles);
  }

  /**
   * Articles for all subscribed channels
   * @param token verification for user
   * @return ArticlesResponse
   */
  @RequestMapping(value = {"/list/feed/all/articles"}, method = RequestMethod.GET)
  public ArticlesResponse getNewestArticles(String token) {
    Long id = userService.verifyToken(token);
    if (id == null) {
      return new ArticlesResponse("wrong token", 0, new ArrayList<>());
    }
    User user = userService.findById(id).get();
    if (user == null) {
      return new ArticlesResponse("user not find", 1, new ArrayList<>());
    }
    List<Article> articles = articleService.find30NewestArticlesFromManyFeeds(user.getRssFeedEntities());
    for (Article article: articles) {
      article.setDescription(article.getDescription().substring(0,50));
    }
    return new ArticlesResponse("succeed", 2, articles);

  }

  /**
   * A article for a given article id
   * @param token verification for user
   * @return ArticleResponse
   */
  @RequestMapping(value = {"/list/article/{id}"}, method = RequestMethod.GET)
  public ArticleResponse getOneArticle(@PathVariable("id") Long id, String token){
    Long user_id = userService.verifyToken(token);
    if (user_id == null) {
      return new ArticleResponse("wrong token", 0);
    }
    Article article = articleService.findById(id);
    if (article == null) {
      return new ArticleResponse("article not find", 1);
    }
    ArticleResponse articleResponse = new ArticleResponse("article find succeed", 2);
    articleResponse.setTitle(article.getTitle());
    articleResponse.setChannelName(article.getRssFeedEntity().getChannelName());
    articleResponse.setLink(article.getLink());
    articleResponse.setPublishedTime(article.getPublishedTime());
    articleResponse.setContent(article.getDescription());
    return articleResponse;
  }

  /**
   * Subscribe a new channel
   * @param token verification for user
   * @param rssFeedEntity a channel entity
   * @return NormalResponse
   */
  @RequestMapping(value = {"/addChannel"}, method = RequestMethod.GET)
  public NormalResponse subscribeChannel(String token, RssFeed rssFeedEntity) {
    Long id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("add a new channel");
    if (id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage("wrong token");
      return normalResponse;
    }
    User user = userService.findById(id).get();
    if (user == null) {
      normalResponse.setMsgCode(1);
      normalResponse.setMessage("user not find");
      return normalResponse;
    }

//    RssFeed rssFeedEntity = new RssFeed();
//    rssFeedEntity.setUrl("http://www.feng.com/rss.xml");
//    rssFeedEntity.setCategory("news");
//    rssFeedEntity.setChannelName("zheng zhi");

    if (!rssFeedService.addChannel(rssFeedEntity, user)) {
      normalResponse.setMsgCode(2);
      normalResponse.setMessage("add failed");
    } else {
      normalResponse.setMsgCode(3);
      normalResponse.setMessage("add succeed");
    }
    return normalResponse;
  }

  /**
   * Unsubscribe a channel
   * @param token verification for user
   * @param channel_id channel id
   * @return NormalResponse
   */
  @RequestMapping(value = {"/deleteChannel"}, method = RequestMethod.GET)
  public NormalResponse deleteChannel(String token, Long channel_id) {
    Long user_id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("delete a channel");
    if (user_id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage("wrong token");
      return normalResponse;
    }
    RssFeed rssFeedEntity = rssFeedService.findById(channel_id).get();
    User user = userService.findById(user_id).get();
    if (rssFeedEntity == null) {
      normalResponse.setMsgCode(1);
      normalResponse.setMessage("channel not find");
      return normalResponse;
    }
    if (!rssFeedService.deleteChannel(rssFeedEntity, user)){
      normalResponse.setMsgCode(2);
      normalResponse.setMessage("delete failed");
    } else {
      normalResponse.setMsgCode(3);
      normalResponse.setMessage("delete succeed");
    }
    return normalResponse;
  }

  @RequestMapping(value = {"/freshChannel"}, method = RequestMethod.GET)
  public NormalResponse freshChannel(String token){
    Long user_id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("fresh channels");
    if (user_id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage("wrong token");
      return normalResponse;
    }
    User user = userService.findById(user_id).get();
    Set<RssFeed> rssFeedSet = user.getRssFeedEntities();
    for(RssFeed rssFeed: rssFeedSet){
      rssFeedService.addArticles(rssFeed);
    }
    normalResponse.setMsgCode(1);
    normalResponse.setMessage("update succeed");
    return normalResponse;
  }

  // 逻辑上存在问题：一用户修改，其它用户都被修改
  // TODO maybe 在建一张只有link的表 用户的分类是另一张表 / 什么都不做，用户无更新权限只能订阅
  @RequestMapping(value = {"/updateChannel"}, method = RequestMethod.GET)
  public NormalResponse updateChannel(String token, RssFeed rssFeedEntity) {
    Long user_id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("update a channel");
    if (user_id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage("wrong token");
      return normalResponse;
    }
    RssFeed existRssFeedEntity = rssFeedService.findById(rssFeedEntity.getId()).get();
    User user = userService.findById(user_id).get();

    if (existRssFeedEntity == null) {
      normalResponse.setMsgCode(1);
      normalResponse.setMessage("channel not find");
      return normalResponse;
    }

    if (rssFeedService.findByChannelName(rssFeedEntity.getChannelName()) != null) {
      normalResponse.setMsgCode(2);
      normalResponse.setMessage("channel name already exists");
      return normalResponse;
    }

    existRssFeedEntity.setChannelName(rssFeedEntity.getChannelName());
    existRssFeedEntity.setCategory(rssFeedEntity.getCategory());

    normalResponse.setMsgCode(3);
    normalResponse.setMessage("update succeed");

    return normalResponse;
  }
}
