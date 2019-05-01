package com.icecream.server.service.rssfeed;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

//    public List<Article> find10NewestArticles() {
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "publishedTime");
//        return articleRepository.findAll(pageRequest).getContent();
//    }

    public List<Article> find30NewestArticlesFromOneFeed(RssFeed rssFeed) {
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
        return articleRepository.findByRssFeedEntity(rssFeed, pageRequest); //TODO 没了GETCONTENT 不知道对不对
    }

    public List<Article> find30NewestArticlesFromManyFeeds(Set<RssFeed> rssFeeds) {
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.Direction.DESC, "publishedTime");
        return articleRepository.findByRssFeedEntityIsIn(rssFeeds, pageRequest);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }

}
