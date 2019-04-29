package com.icecream.server.dao;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
  List<RssFeed> findByUserEntity(User userEntity);
}
