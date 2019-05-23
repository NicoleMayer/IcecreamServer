//package com.icecream.server;
//
//import com.icecream.server.dao.RssFeedRepository;
//import com.icecream.server.entity.RssFeed;
//import com.icecream.server.service.rssfeed.ArticleService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.Assert.fail;
//
///**
// * ArticleServiceImpl Tester.
// * @author nicolemayer
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IceCreamServerApplication.class)
//public class ArticleServiceImplTest {
//
//  @Autowired
//  private transient ArticleService articleService;
//
//  @Autowired
//  private transient RssFeedRepository rssFeedRepository;
//
//  /**
//  * test for finding 30 newest articles from one feed
//  */
//  @Test
//  public void testFind30NewestArticlesFromOneFeed() throws Exception {
//    RssFeed rssFeed = rssFeedRepository.findByUrl("https://36kr.com/feed");
//    try {
//      articleService.find30NewestArticlesFromOneFeed(rssFeed);
//    }
//    catch (Exception e){
//      fail("shouldn't fail");
//    }
//  }
//
//  /**
//  * test for finding 30 newest articles from many feeds
//  */
//  @Test
//  public void testFind30NewestArticlesFromManyFeeds() throws Exception {
//    Set<RssFeed> rssFeedSet = new HashSet<>();
//    rssFeedSet.add(rssFeedRepository.findByUrl("https://36kr.com/feed"));
//    rssFeedSet.add(rssFeedRepository.findByUrl("http://www.feng.com/rss.xml"));
//    try {
//      articleService.find30NewestArticlesFromManyFeeds(rssFeedSet);
//    }
//    catch (Exception e){
//      fail("shouldn't fail");
//    }
//  }
//
//}
