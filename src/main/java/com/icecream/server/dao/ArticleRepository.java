package com.icecream.server.dao;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findByRssFeedEntity(RssFeed rssFeedEntity);

  List<Article> findByRssFeedEntity(RssFeed rssFeedEntity, Pageable pageable);

  List<Article> findByRssFeedEntityIsIn(Set<RssFeed> rssFeedEntity);

  List<Article> findByRssFeedEntityIsIn(Set<RssFeed> rssFeedEntity, Pageable pageable);

  Article findByRssFeedEntityAndLink(RssFeed rssFeedEntity, String link);

  List<Article> findByTitleIsLike(String searchWords);
}
