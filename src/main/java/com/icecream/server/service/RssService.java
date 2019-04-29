package com.icecream.server.service;

import com.icecream.server.entity.Article;
import com.icecream.server.exception.RSSException;

import java.io.File;
import java.util.List;


public interface RssService {

    List<Article> getItems(File file) throws RSSException, RSSException;

    List<Article> getItems(String url) throws RSSException;

}
