package com.icecream.server.service.rssfeed;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * find 30 newest articles from one feed
     * @param rssFeed one rssfeed to find newest articles
     * @return 30 newest articles
     */
    public List<Article> find30NewestArticlesFromOneFeed(RssFeed rssFeed) {
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
        return articleRepository.findByRssFeedEntity(rssFeed, pageRequest);
    }

    /**
     * find 30 newest articles from many feeds
     * @param rssFeeds rssfeeds to find newest articles
     * @return 30 newest articles
     */
    public List<Article> find30NewestArticlesFromManyFeeds(Set<RssFeed> rssFeeds) {
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
        return articleRepository.findByRssFeedEntityIsIn(rssFeeds, pageRequest);
    }

}
