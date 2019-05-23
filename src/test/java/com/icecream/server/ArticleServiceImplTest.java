package com.icecream.server;

import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.rssfeed.ArticleService;
import com.icecream.server.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * ArticleServiceImpl Tester.
 *
 * @author nicolemayer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IceCreamServerApplication.class)
public class ArticleServiceImplTest {

  @Autowired
  private transient ArticleService articleService;

  @Autowired
  private transient UserService userService;

  @Autowired
  private transient RssFeedRepository rssFeedRepository;

  /**
   * Test for finding 30 newest articles from one feed.
   */
  @Test
  public void testFind30NewestArticlesFromOneFeed() throws Exception {
    RssFeed rssFeed = rssFeedRepository.findByUrl("https://36kr.com/feed");
    try {
      articleService.find30NewestArticlesFromOneFeed(rssFeed);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
    rssFeed = rssFeedRepository.findByUrl("http://www.feng.com/rss.xml");
    try {
      articleService.find30NewestArticlesFromOneFeed(rssFeed);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
  }

  /**
   * Test for finding 30 newest articles from many feeds.
   */
  @Test
  public void testFind30NewestArticlesFromManyFeeds() throws Exception {
    Set<RssFeed> rssFeedSet = new HashSet<>();
    rssFeedSet.add(rssFeedRepository.findByUrl("https://36kr.com/feed"));
    try {
      articleService.find30NewestArticlesFromManyFeeds(rssFeedSet);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }

    rssFeedSet.add(rssFeedRepository.findByUrl("http://www.feng.com/rss.xml"));
    try {
      articleService.find30NewestArticlesFromManyFeeds(rssFeedSet);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
  }

  /**
   * Test for finding 30 newest articles from many feeds.
   */
  @Test
  public void testFind30NewestArticlesFromManyFeeds2() throws Exception {
    List<RssFeed> rssFeedSet = new ArrayList<>();
    rssFeedSet.add(rssFeedRepository.findByUrl("https://36kr.com/feed"));
    try {
      articleService.find30NewestArticlesFromManyFeeds(rssFeedSet);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }

    rssFeedSet.add(rssFeedRepository.findByUrl("http://www.feng.com/rss.xml"));
    try {
      articleService.find30NewestArticlesFromManyFeeds(rssFeedSet);
    }
    catch (Exception e){
      fail("shouldn't fail");
    }
  }

  /**
   * Test for like an article.
   */
  @Test
  public void testLikeArticle() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    assertTrue(articleService.likeArticle(user,(long)75)); //TODO article with 75 must in the database
    assertFalse(articleService.likeArticle(user,(long)75));
    articleService.unlikeArticle(user,(long)75);
  }

  /**
   * Test for unlike an article.
   */
  @Test
  public void testUnlikeArticle() throws Exception {
    User user = userService.findById(1).isPresent()?userService.findById(1).get():null;
    articleService.likeArticle(user,(long)76);
    assertTrue(articleService.unlikeArticle(user,(long)76));
    assertFalse(articleService.unlikeArticle(user,(long)78));//TODO article with 78 must in the database
  }

}
