package com.icecream.server.dao;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
  List<RssFeed> findByUserEntities(User user);

  List<RssFeed> findByCategory(String category);

  RssFeed findByUrl(String url);

  RssFeed findByChannelName(String channelName);

  Optional<RssFeed> findById(Long id);

}
