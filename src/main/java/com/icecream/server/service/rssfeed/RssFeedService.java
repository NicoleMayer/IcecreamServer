package com.icecream.server.service.rssfeed;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.exception.RssException;

import java.util.List;
import java.util.Optional;


/**
 * This is the Service interface for {@link RssFeed}.
 *
 * @author NicoleMayer
 */
public interface RssFeedService {

  boolean addChannel(String url, User user);

  void addArticles(RssFeed rssFeedEntity);

  List<Article> crawlArticles(String url) throws RssException;

  boolean deleteChannel(String url, User user);

  Optional<RssFeed> findById(long id);

}
