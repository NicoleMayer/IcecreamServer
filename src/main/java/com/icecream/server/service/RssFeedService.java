package com.icecream.server.service;

import com.icecream.server.entity.RssFeed;

public interface RssFeedService {

    void save(RssFeed rssFeedEntity, String name);

    void saveAll(RssFeed rssFeedEntity);

    void reloadChannels();

    RssFeed findOne(Long id);

    void delete(RssFeed rssFeedEntity);
}
