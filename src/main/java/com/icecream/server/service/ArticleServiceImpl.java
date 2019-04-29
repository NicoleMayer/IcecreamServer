package com.icecream.server.service;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> find10NewestEntries() {
//        PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC, "publishedTime");
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "publishedTime");
        return articleRepository.findAll(pageRequest).getContent();
    }

}
