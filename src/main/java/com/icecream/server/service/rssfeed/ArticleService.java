package com.icecream.server.service.rssfeed;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ArticleService {

    List<Article> findAll();
//    List<Article> find10NewestArticles(); TODO 用户没订阅过的也显示  应该没有使用场景

    List<Article> find30NewestArticlesFromOneFeed(RssFeed rssFeed);

    List<Article> find30NewestArticlesFromManyFeeds(Set<RssFeed> rssFeeds);

    Article findById(Long id);

}
