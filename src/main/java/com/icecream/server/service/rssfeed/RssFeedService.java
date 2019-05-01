package com.icecream.server.service.rssfeed;

import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.exception.RSSException;

import java.util.List;

public interface RssFeedService {

    boolean addChannel(RssFeed rssFeedEntity, User user) throws RSSException;

    void addArticles(RssFeed rssFeedEntity);

    List<Article> crawlArticles(String url) throws RSSException;

    void reloadChannels();

    void deleteChannel(RssFeed rssFeedEntity);
}
