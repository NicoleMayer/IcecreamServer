package com.icecream.server.service.rssfeed;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;

import java.util.List;
import java.util.Set;


public interface ArticleService {

    List<Article> find30NewestArticlesFromOneFeed(RssFeed rssFeed);

    List<Article> find30NewestArticlesFromManyFeeds(Set<RssFeed> rssFeeds);

}
