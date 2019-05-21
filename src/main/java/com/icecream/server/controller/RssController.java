package com.icecream.server.controller;

import com.icecream.server.client.ArticleResponse;
import com.icecream.server.client.ArticlesResponse;
import com.icecream.server.client.FeedsResponse;
import com.icecream.server.client.NormalResponse;
import com.icecream.server.dao.ArticleRepository;
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
 * This class is a rest controller for rss subscribe.
 *
 * @author NicoleMayer
 */
@RestController
public class RssController {

  private final transient UserService userService;

  private final transient RssFeedService rssFeedService;

  private final transient ArticleService articleService;

  private final transient ArticleRepository articleRepository;


  private static final transient String WRONG_TOKEN = "wrong token";
  private static final transient String USER_NOT_FIND = "user not find";

  /**
   * Constructor of RssController.
   *
   * @param userService       UserService class
   * @param rssFeedService    RssFeedService class
   * @param articleService    ArticleService class
   * @param articleRepository ArticleRepository class
   */
  public RssController(UserService userService, RssFeedService rssFeedService,
                       ArticleService articleService, ArticleRepository articleRepository) {
    this.userService = userService;
    this.rssFeedService = rssFeedService;
    this.articleService = articleService;
    this.articleRepository = articleRepository;
  }

  /**
   * Welcome page.
   *
   * @return Welcome message
   */
  @RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String hello() {
    return "welcome to icecream server";
  }

  /**
   * User's subscribed channels.
   *
   * @param token verification for user
   * @return FeedsResponse
   */
  @RequestMapping(value = {"/list/feeds"}, method = RequestMethod.GET)
  public FeedsResponse getChannelListForCurrentUser(String token) {
    Long id = userService.verifyToken(token);
    if (id == null) {
      return new FeedsResponse(WRONG_TOKEN, 0, new HashSet<>());
    }
    User user = userService.findById(id).orElse(null);
    if (user == null) {
      return new FeedsResponse(USER_NOT_FIND, 1, new HashSet<>());
    }
    return new FeedsResponse("succeed", 2, user.getRssFeedEntities());
  }

  /**
   * Articles for a subscribed channel.
   *
   * @param token verification for user
   * @return ArticlesResponse
   */
  @RequestMapping(value = {"/list/feed/{id}/articles"}, method = RequestMethod.GET)
  public ArticlesResponse getNewestArticlesFromOneFeed(@PathVariable("id") Long id, String token) {
    Long userId = userService.verifyToken(token);
    if (userId == null) {
      return new ArticlesResponse(WRONG_TOKEN, 0, new ArrayList<>());
    }
    User user = userService.findById(userId).orElse(null);
    if (user == null) {
      return new ArticlesResponse(USER_NOT_FIND, 1, new ArrayList<>());
    }
    RssFeed rssFeed = rssFeedService.findById(id).orElse(null);
    if (rssFeed == null) {
      return new ArticlesResponse("feed not find", 2, new ArrayList<>());
    }
    List<Article> articles = articleService.find30NewestArticlesFromOneFeed(rssFeed);
    for (Article article : articles) {
      String desc = article.getDescription();
      if (desc.length() > 50) {
        article.setDescription(desc.substring(0, 50));
      } else {
        article.setDescription(desc);
      }
    }
    return new ArticlesResponse("succeed", 3, articles);
  }

  /**
   * Articles for all subscribed channels.
   *
   * @param token verification for user
   * @return ArticlesResponse
   */
  @RequestMapping(value = {"/list/feed/all/articles"}, method = RequestMethod.GET)
  public ArticlesResponse getNewestArticles(String token) {
    Long userId = userService.verifyToken(token);
    if (userId == null) {
      return new ArticlesResponse(WRONG_TOKEN, 0, new ArrayList<>());
    }
    User user = userService.findById(userId).orElse(null);
    if (user == null) {
      return new ArticlesResponse(USER_NOT_FIND, 1, new ArrayList<>());
    }
    List<Article> articles = articleService.find30NewestArticlesFromManyFeeds(
        user.getRssFeedEntities());
    for (Article article : articles) {
      String desc = article.getDescription();
      if (desc.length() > 50) {
        article.setDescription(desc.substring(0, 50));
      } else {
        article.setDescription(desc);
      }
    }
    return new ArticlesResponse("succeed", 2, articles);

  }

  /**
   * A article for a given article id.
   *
   * @param token verification for user
   * @return ArticleResponse
   */
  @RequestMapping(value = {"/list/article/{id}"}, method = RequestMethod.GET)
  public ArticleResponse getOneArticle(@PathVariable("id") Long id, String token) {
    Long userId = userService.verifyToken(token);
    if (userId == null) {
      return new ArticleResponse(WRONG_TOKEN, 0);
    }
    Article article = articleRepository.findById(id).isPresent()
        ? articleRepository.findById(id).get() : null;
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
   * Subscribe a new channel.
   *
   * @param token verification for user
   * @param url   RSS feed url
   * @return NormalResponse
   */
  @RequestMapping(value = {"/addChannel"}, method = RequestMethod.GET)
  public NormalResponse subscribeChannel(String token, String url) {
    Long id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("add a new channel");
    if (id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage(WRONG_TOKEN);
      return normalResponse;
    }
    User user = userService.findById(id).orElse(null);
    if (user == null) {
      normalResponse.setMsgCode(1);
      normalResponse.setMessage(USER_NOT_FIND);
      return normalResponse;
    }

    if (!rssFeedService.addChannel(url, user)) {
      normalResponse.setMsgCode(2);
      normalResponse.setMessage("add failed");
    } else {
      normalResponse.setMsgCode(3);
      normalResponse.setMessage("add succeed");
    }
    return normalResponse;
  }

  /**
   * Unsubscribe a channel.
   *
   * @param token verification for user
   * @param url   RSS feed url
   * @return NormalResponse
   */
  @RequestMapping(value = {"/deleteChannel"}, method = RequestMethod.GET)
  public NormalResponse deleteChannel(String token, String url) {
    Long userId = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("delete a channel");
    if (userId == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage(WRONG_TOKEN);
      return normalResponse;
    }
    User user = userService.findById(userId).orElse(null);
    if (user == null) {
      normalResponse.setMsgCode(1);
      normalResponse.setMessage(USER_NOT_FIND);
      return normalResponse;
    }
    if (!rssFeedService.deleteChannel(url, user)) {
      normalResponse.setMsgCode(2);
      normalResponse.setMessage("delete failed");
    } else {
      normalResponse.setMsgCode(3);
      normalResponse.setMessage("delete succeed");
    }
    return normalResponse;
  }

  @RequestMapping(value = {"/freshChannel"}, method = RequestMethod.GET)
  public NormalResponse freshChannel(String token) {
    Long user_id = userService.verifyToken(token);
    NormalResponse normalResponse = new NormalResponse("fresh channels");
    if (user_id == null) {
      normalResponse.setMsgCode(0);
      normalResponse.setMessage("wrong token");
      return normalResponse;
    }
    User user = userService.findById(user_id).orElse(null);
    Set<RssFeed> rssFeedSet = user.getRssFeedEntities();
    for (RssFeed rssFeed : rssFeedSet) {
      rssFeedService.addArticles(rssFeed);
    }
    normalResponse.setMsgCode(1);
    normalResponse.setMessage("update succeed");
    return normalResponse;
  }
}
