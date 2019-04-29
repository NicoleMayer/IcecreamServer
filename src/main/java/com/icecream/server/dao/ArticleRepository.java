package com.icecream.server.dao;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
//  List<Article> findByRssFeedEntity(RssFeed rssFeedEntity, Pageable pageable);

  List<Article> findByRssFeedEntity(RssFeed rssFeedEntity);
  Article findByRssFeedEntityAndLink(RssFeed rssFeedEntity, String link);
}
