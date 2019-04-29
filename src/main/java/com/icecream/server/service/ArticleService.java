package com.icecream.server.service;

import com.icecream.server.entity.Article;
import java.util.List;


public interface ArticleService {

    List<Article> findAll();

    List<Article> find10NewestEntries();

}
