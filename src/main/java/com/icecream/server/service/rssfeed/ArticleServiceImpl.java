package com.icecream.server.service.rssfeed;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private transient ArticleRepository articleRepository;

  @Autowired
  private transient UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
  /**
   * find 30 newest articles from one feed.
   *
   * @param rssFeed one rssfeed to find newest articles
   * @return 30 newest articles
   */
  public List<Article> find30NewestArticlesFromOneFeed(RssFeed rssFeed) {
    PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
    return articleRepository.findByRssFeedEntity(rssFeed, pageRequest);
  }

  /**
   * find 30 newest articles from many feeds.
   *
   * @param rssFeeds rssfeeds to find newest articles
   * @return 30 newest articles
   */
  public List<Article> find30NewestArticlesFromManyFeeds(Set<RssFeed> rssFeeds) {
    PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
    return articleRepository.findByRssFeedEntityIsIn(rssFeeds, pageRequest);
  }

  /**
   * find 30 newest articles from many feeds.
   *
   * @param rssFeeds rssfeeds to find newest articles
   * @return 30 newest articles
   */
  public List<Article> find30NewestArticlesFromManyFeeds(List<RssFeed> rssFeeds) {
    Set<RssFeed> rssFeeds1 = new HashSet(rssFeeds);
    PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
    return articleRepository.findByRssFeedEntityIsIn(rssFeeds1, pageRequest);
  }

  /**
   * Like a given article.
   *
   * @param user who want to collect an article
   * @param id which article to collect
   * @return succeed or not
   */
  @Override
  public boolean likeArticle(User user, Long id) {
    Article article = articleRepository.findById(id).orElse(null);
    if (article == null
            || article.getUserEntities().contains(user)) { //already like the article
      return false;
    }

    if (user.getCollectedArticles().add(article)) {
      userRepository.save(user);
      logger.info("like article succeed");
      return true;
    }
    return false;
  }

  /**
   * Unlike a given article.
   *
   * @param user who want to uncollect an article
   * @param id which article to uncollect
   * @return succeed or not
   */
  @Override
  public boolean unlikeArticle(User user, Long id) {
    Article article = articleRepository.findById(id).orElse(null);
    if (article == null
            || !article.getUserEntities().contains(user)) { //don't have the article
      return false;
    }

    if (user.getCollectedArticles().remove(article)) {
      userRepository.save(user);
      logger.info("unlike article succeed");
      return true;
    }
    return false;
  }
}

